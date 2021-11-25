package com.example.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/24 下午8:32
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
