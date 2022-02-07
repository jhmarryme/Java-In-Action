package com.example;

import feign.Feign;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/13 下午12:30
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class HystrixApplication {

    public static void main(String[] args) {
        // try {
        //     System.out.println(Feign.configKey(MyService.class, MyService.class.getMethod("retry", int.class)));
        // } catch (NoSuchMethodException e) {
        //     e.printStackTrace();
        // }
        new SpringApplicationBuilder(HystrixApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
