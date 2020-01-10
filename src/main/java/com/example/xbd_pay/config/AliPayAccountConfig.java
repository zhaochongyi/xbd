package com.example.xbd_pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xiongbenda
 * @create 2020-01-02 17:03
 */
@Data
@Component
@ConfigurationProperties(prefix = "ali")
public class AliPayAccountConfig extends BastPayAccountConfig {
    private String appId;
    private String privateKey;
    private String aliPayPublicKey;
}
