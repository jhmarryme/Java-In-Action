package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/28 上午9:03
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class GatewaySampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewaySampleApplication.class, args);
    }
}
