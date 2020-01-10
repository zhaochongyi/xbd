package com.example.xbd_pay.controller;

import com.example.xbd_pay.config.BastPayConfig;
import com.example.xbd_pay.enums.ResultEnum;
import com.example.xbd_pay.exception.XbdPayException;
import com.example.xbd_pay.pojo.PayInfo;
import com.example.xbd_pay.service.impl.PayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xiongbenda
 * @create 2019-12-29 17:43
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    PayService payService;

    @Autowired
    BastPayConfig bastPayConfig;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("amount")BigDecimal amount,
                               @RequestParam("payType")BestPayTypeEnum payTypeEnum){
        PayResponse payResponse = payService.create(orderId, amount,payTypeEnum);
        Map<String,String> map = new HashMap<String,String>();
        if (payTypeEnum == BestPayTypeEnum.WXPAY_NATIVE){
            map.put("codeUrl",payResponse.getCodeUrl());
            map.put("orderId",orderId);
            map.put("returnUrl",bastPayConfig.wxPayConfig().getReturnUrl());
            return  new ModelAndView("createWXPayNative",map);
        }else if (payTypeEnum == BestPayTypeEnum.ALIPAY_PC){
            map.put("body",payResponse.getBody());
            return  new ModelAndView("createALIPayPc",map);
        }
        throw new XbdPayException(ResultEnum.ERROR_PAY_PLATFORM,payResponse.getOrderId());
    }
    @ResponseBody
    @RequestMapping("/asyncNotify")
    public String asyncNotify(@RequestBody String notifyData ){
        return payService.asyncNotify(notifyData);
    }


    @ResponseBody
    @RequestMapping("/paySuccess")
    public String paySuccess(){
        return ResultEnum.PAY_SUCCESS.getMsg();
    }

    @ResponseBody
    @GetMapping("/queryByOrderId")
    public PayInfo queryByOrderId(@RequestParam("orderId")String orderId){
        return payService.queryByOrderId(orderId);
    }


}
