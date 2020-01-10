package com.example.xbd_pay.vo;

import java.util.Date;

@lombok.Data
public class PayNotifyVo {
    //订单号
    private String orderNo;
    //支付时间
    private Date payTime;
    //支付状态
    private String platformStatus;
}
