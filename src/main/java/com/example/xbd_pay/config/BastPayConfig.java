package com.example.xbd_pay.config;

import com.lly835.bestpay.config.AliPayConfig;
import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author xiongbenda
 * @create 2019-12-29 21:58
 */
@Component
public class BastPayConfig {

    @Autowired
    private WxAccountConfig wxAccountConfig;
    @Autowired
    private AliPayAccountConfig aliPayAccountConfig;
    /**
     * 项目启动的时候执行
     * @return
     */
    @Bean
    public BestPayService bestPayService(WxPayConfig wxPayConfig,AliPayConfig aliPayConfig){
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);
        bestPayService.setAliPayConfig(aliPayConfig);
        return bestPayService;
    }

    /**
     * 微信支付配置
     * @return
     */
    @Bean
    public WxPayConfig wxPayConfig(){
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId(wxAccountConfig.getAppId()); //app应用appid
        wxPayConfig.setMchId(wxAccountConfig.getMchId());  //商户号
        wxPayConfig.setMchKey(wxAccountConfig.getMchKey());  //商户密钥
        wxPayConfig.setNotifyUrl(wxAccountConfig.getNotifyUrl());// 异步通知地址
        wxPayConfig.setReturnUrl(wxAccountConfig.getReturnUrl());// 异步通知地址
        return wxPayConfig;
    }

    /**
     * 阿里支付配置
     * @return
     */
    @Bean
    public AliPayConfig aliPayConfig(){
        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId(aliPayAccountConfig.getAppId());
        aliPayConfig.setPrivateKey(aliPayAccountConfig.getPrivateKey());
        aliPayConfig.setAliPayPublicKey(aliPayAccountConfig.getAliPayPublicKey());
        aliPayConfig.setReturnUrl(aliPayAccountConfig.getReturnUrl());
        aliPayConfig.setNotifyUrl(aliPayAccountConfig.getNotifyUrl()); // 异步通知地址
        return aliPayConfig;
    }

}
