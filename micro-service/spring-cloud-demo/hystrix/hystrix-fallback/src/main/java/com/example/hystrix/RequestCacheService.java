package com.example.hystrix;

import com.example.MyService;
import com.example.springcloud.Friend;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RequestCacheService {

    @Autowired
    private MyService service;

    @SneakyThrows
    @CacheResult
    @HystrixCommand(commandKey = "cacheKey")
    public Friend requestCache(@CacheKey String name) {
        log.info("request cache " + name);
        Friend friend = new Friend();
        friend.setName(name);
        friend = service.sayHiPost(friend);
        log.info("after requesting cache " + name);
        // Thread.sleep(3000);
        return friend;
    }

}