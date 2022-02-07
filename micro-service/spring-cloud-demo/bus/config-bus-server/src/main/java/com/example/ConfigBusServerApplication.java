package com.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/20 下午3:32
 */
@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigBusServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigBusServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}