package com.example.xbd_pay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan(basePackages = "com.example.xbd_pay.dao")
@SpringBootApplication
public class XbdPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XbdPayApplication.class, args);
    }

}
