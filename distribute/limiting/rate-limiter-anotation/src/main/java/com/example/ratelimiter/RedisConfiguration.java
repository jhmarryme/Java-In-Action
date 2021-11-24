package com.example.ratelimiter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/24 上午9:42
 */
@Configuration
public class RedisConfiguration {

    /** 如果本地也配置了StringRedisTemplate，可能会产生冲突
        可以指定@Primary，或者指定加载特定的@Qualifier
        可以自动注入, 这里就不用配置了
     */
    // @Bean
    // public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    //     return new StringRedisTemplate(factory);
    // }

    @Bean
    public RedisScript<Boolean> loadRedisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setLocation(new ClassPathResource("rate-limiter.lua"));
        redisScript.setResultType(Boolean.class);
        return redisScript;
    }
}
