package com.example.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/2 上午9:14
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerAdvancedApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsumerAdvancedApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
