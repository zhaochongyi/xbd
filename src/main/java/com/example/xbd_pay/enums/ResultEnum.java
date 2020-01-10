package com.example.xbd_pay.enums;

/**
 * 返回
 */
public enum  ResultEnum {
    UNKNOW_EXCEPTION(-1,"系统级错误"),
    UNKNOWINFO_EXCEPTION(100,"查询不到支付记录,orderNo="),
    INCORRECT_AMOUNT_EXCEPTION(101,"银行返回金额与数据库里的不一致,orderNo="),
    ERROR_PAY_PLATFORM(102,"错误的支付平台,orderNo="),
    ALI_NOTIFY_SUCCESS(0,"success"),//阿里异步通知成功
    WX_NOTIFY_SUCCESS(0,"<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>"),//微信异步通知成功
    PAY_SUCCESS(0,"支付成功"),

    ;
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
