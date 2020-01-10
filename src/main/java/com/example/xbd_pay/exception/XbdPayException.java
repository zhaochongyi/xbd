package com.example.xbd_pay.exception;

import com.example.xbd_pay.enums.ResultEnum;

/***
 * 统一异常处理
 */
public class XbdPayException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public XbdPayException(ResultEnum resultEnum,String orderNo){
        super(resultEnum.getMsg()+orderNo);
        this.code = resultEnum.getCode();
    }

}
