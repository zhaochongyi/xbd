package com.example.xbd_pay.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BastPayAccountConfig {
    private String notifyUrl;
    private String returnUrl;
}
