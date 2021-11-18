package com.example.redissonlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
// 引入Redisson的方式2: Spring项目引入Redisson, 这里模拟引入xml的方式
@ImportResource("classpath*:redisson.xml")
public class RedissonLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissonLockApplication.class, args);
    }

}
