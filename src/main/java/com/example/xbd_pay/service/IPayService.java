package com.example.xbd_pay.service;

import com.example.xbd_pay.pojo.PayInfo;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * @Author xiongbenda
 * @create 2019-12-29 16:44
 */
public interface IPayService {
    /**
     * 创建/发起支付
     * @param orderId 订单号
     * @param amount 订单金额
     */
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum payTypeEnum);

    String asyncNotify(String notifyData);

    PayInfo queryByOrderId(String orderId);
}
