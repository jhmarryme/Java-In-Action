package com.example.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class Controller {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String sayHi() {

        ServiceInstance instance = loadBalancerClient.choose("eureka-client");

        if (instance == null) {
            return "No available instances";
        }

        String url = String.format("http://%s:%s/sayHi", instance.getHost(), instance.getPort());
        log.info("url is {}", url);

        return restTemplate.getForObject(url, String.class);
    }

    @PostMapping("/hello")
    public Friend sayHiPost(@RequestBody Friend friend) {

        ServiceInstance instance = loadBalancerClient.choose("eureka-client");

        if (instance == null) {
            return null;
        }

        String url = String.format("http://%s:%s/sayHi", instance.getHost(), instance.getPort());
        log.info("url is {}", url);

        return restTemplate.postForObject(url, friend, Friend.class);
    }

}