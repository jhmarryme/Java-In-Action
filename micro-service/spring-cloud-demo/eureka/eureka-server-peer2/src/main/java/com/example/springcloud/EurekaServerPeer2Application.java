package com.example.springcloud;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/24 下午8:32
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerPeer2Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServerPeer2Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
