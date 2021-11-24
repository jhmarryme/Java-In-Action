package com.example.ratelimiter;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/24 上午9:55
 */
@Service
@Slf4j
public class AccessLimiter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean> rateLimitScript;

    public void limitAccess(String key, Integer limit) {
        Boolean acquired = stringRedisTemplate.execute(
                // lua脚本
                rateLimitScript,
                // lua中的keys
                Lists.newArrayList(key),
                // lua中的argv
                limit.toString()
        );
        if (Boolean.FALSE.equals(acquired)) {
            log.error("Your access is blocked, key = {}", key);
            throw new RuntimeException("Your access is blocked");
        }
    }
}
