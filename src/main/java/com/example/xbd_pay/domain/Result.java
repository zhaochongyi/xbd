package com.example.xbd_pay.domain;

import lombok.Data;

/**
 * 通用返回
 * @param <T>
 */
@Data
public class Result<T> {
    /** 状态码 **/
    private Integer code;
    /** 提示信息 **/
    private String msg;
    /** 具体内容 **/
    private T data;
}
