package com.example.distributelock.controller;

import com.example.distributelock.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于Redis的Set NX实现分布式锁
 * @author Jiahao Wang
 */
@RestController
@Slf4j
public class RedisLockController {
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("redisLock")
    public String redisLock() {
        log.info("我进入了方法！");
        try (RedisLock redisLock = new RedisLock(redisTemplate, "redisKey", 30)) {
            if (redisLock.getLock()) {
                log.info("我进入了锁！！");
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return "方法执行完成";
    }

}
