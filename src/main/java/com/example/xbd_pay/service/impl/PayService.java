package com.example.xbd_pay.service.impl;

import com.example.xbd_pay.dao.PayInfoMapper;
import com.example.xbd_pay.enums.PayPlatformEnum;
import com.example.xbd_pay.enums.ResultEnum;
import com.example.xbd_pay.exception.XbdPayException;
import com.example.xbd_pay.pojo.PayInfo;
import com.example.xbd_pay.vo.PayNotifyVo;
import com.google.gson.Gson;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.xbd_pay.service.IPayService;

import java.math.BigDecimal;

/**
 * @Author xiongbenda
 * @create 2019-12-29 16:47
 */
@Slf4j
@Service
public class PayService implements IPayService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private BestPayService bestPayService;

    @Autowired
    private PayInfoMapper payInfoMapper;
    /**
     * 创建/发起支付
     * @param orderId 订单号
     * @param amount 订单金额
     */
    @Override
    public PayResponse create(String orderId, BigDecimal amount,BestPayTypeEnum payTypeEnum) {
        PayInfo payInfo = new PayInfo(orderId,
                            PayPlatformEnum.getByBestPayTypeEnum(payTypeEnum).getCode(),
                            OrderStatusEnum.NOTPAY.name(),
                            amount);
        //插入数据库
        payInfoMapper.insertSelective(payInfo);

        PayRequest request = new PayRequest();
        request.setOrderName("5372117-xiongbenda");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(payTypeEnum);

        PayResponse response = bestPayService.pay(request);
        log.info("发起支付 response={}", response);

        return response;
    }

    /**
     * 异步通知处理
     * @param notifyData
     */
    @Override
    public String asyncNotify(String notifyData) {
        //1,签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("异步通知 response={}",payResponse);
        //2.金额校验
        PayInfo payInfo = payInfoMapper.selectByOrderNo(payResponse.getOrderId());
        if (payInfo == null){
            throw new XbdPayException(ResultEnum.UNKNOWINFO_EXCEPTION,payResponse.getOrderId());
        }
        if (!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS.name())){
            if (payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount())) != 0){
                throw new XbdPayException(ResultEnum.INCORRECT_AMOUNT_EXCEPTION,payResponse.getOrderId());
            }
            //3.修改订单支付状态
            payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
            payInfo.setPlatformNumber(payResponse.getOutTradeNo());
            payInfoMapper.updateByPrimaryKeySelective(payInfo);
        }
        PayNotifyVo  payNotifyVo = new PayNotifyVo();
        payNotifyVo.setOrderNo(payInfo.getOrderNo());
        payNotifyVo.setPayTime(payInfo.getCreateTime());
        payNotifyVo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
        // pay发送MQ消息，shop接收MQ消息
        amqpTemplate.convertAndSend("payNotify",new Gson().toJson(payNotifyVo));


        //4.告诉微信
       if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.WX){
           return ResultEnum.WX_NOTIFY_SUCCESS.getMsg();
       }else if (payResponse.getPayPlatformEnum() == BestPayPlatformEnum.ALIPAY){
           return ResultEnum.ALI_NOTIFY_SUCCESS.getMsg();
       }
        throw new XbdPayException(ResultEnum.ERROR_PAY_PLATFORM,payResponse.getOrderId());
    }

    @Override
    public PayInfo queryByOrderId(String orderId) {
        return  payInfoMapper.selectByOrderNo(orderId);
    }
}
