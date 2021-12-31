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
public class SleuthTraceAMain {

    @LoadBalanced
    @Bean
    public RestTemplate lb() {
        return new RestTemplate();
    }

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/trace-a")
    public String traceA() {
        log.info("-------Trace A");
        return restTemplate.getForEntity("http://sleuth-trace-b/trace-b", String.class).getBody();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(SleuthTraceAMain.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
