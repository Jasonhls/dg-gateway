package com.dg.mall.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "com.dg.mall")
@SpringBootApplication
public class DgGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DgGatewayApplication.class, args);
    }

}
