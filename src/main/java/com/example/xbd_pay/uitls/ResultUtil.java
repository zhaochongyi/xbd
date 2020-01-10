package com.example.xbd_pay.uitls;

import com.example.xbd_pay.domain.Result;

public class ResultUtil {
    /**
     * 返回成功
     * @param object
     * @return
     */
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }
    /**
     * 返回成功
     * @return
     */
    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code,String message){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(message);
        return result;
    }




}
