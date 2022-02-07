package com.example;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/29 下午3:26
 */
@SpringBootTest
public class RedissonTest {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
        redissonClient.getBucket("hello").set("bug");
        String test = (String) redissonClient.getBucket("hello").get();
        System.out.println(test);
    }

    @Test
    void testRedisTemplate() {
        redisTemplate.opsForValue().set("wjh", "wjh");
        String test = (String) redisTemplate.opsForValue().get("wjh");
        System.out.println(test);
    }
}
