package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/31 上午10:21
 */
@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@RestController
public class SleuthTraceBMain {

    @LoadBalanced
    @Bean
    public RestTemplate lb() {
        return new RestTemplate();
    }

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/trace-b")
    public String traceB() {
        log.info("-------Trace B");
        return "trace B";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(SleuthTraceBMain.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
