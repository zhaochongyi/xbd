package com.example.xbd_pay.service.impl;

import com.example.xbd_pay.XbdPayApplicationTests;
import com.example.xbd_pay.vo.PayNotifyVo;
import com.google.gson.Gson;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author xiongbenda
 * @create 2019-12-29 17:01
 */
public class PayServiceTest extends XbdPayApplicationTests {

    @Autowired
    private PayService payService;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void create() {
        payService.create("1234567891234562", BigDecimal.valueOf(0.01), BestPayTypeEnum.WXPAY_NATIVE);
    }

    @Test
    public void sendMQsg(){
        PayNotifyVo payNotifyVo = new PayNotifyVo();
        payNotifyVo.setOrderNo("ON200109170444865CXNuIloL");
        payNotifyVo.setPayTime(new Date());
        payNotifyVo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
        // pay发送MQ消息，shop接收MQ消息
        amqpTemplate.convertAndSend("payNotify",new Gson().toJson(payNotifyVo));
    }
}