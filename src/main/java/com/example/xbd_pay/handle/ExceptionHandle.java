package com.example.xbd_pay.handle;

import com.example.xbd_pay.domain.Result;
import com.example.xbd_pay.enums.ResultEnum;
import com.example.xbd_pay.exception.XbdPayException;
import com.example.xbd_pay.uitls.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 捕获异常
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result handle(Exception e){
        if (e instanceof XbdPayException){
            XbdPayException xbdPayException = (XbdPayException) e;
            return ResultUtil.error(xbdPayException.getCode(),xbdPayException.getMessage());
        }
        logger.error("系统级别错误{}",e);
        return ResultUtil.error(ResultEnum.UNKNOW_EXCEPTION.getCode(),ResultEnum.UNKNOW_EXCEPTION.getMsg());

    }

}
