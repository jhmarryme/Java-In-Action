package com.example;

import com.example.hystrix.RequestCacheService;
import com.example.springcloud.Friend;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/13 下午12:41
 */
@RestController
public class Controller {

    @Autowired
    private MyService myService;

    @Autowired
    private RequestCacheService requestCacheService;

    @GetMapping("/fallback")
    public String fallback() {
        return myService.error();
    }

    @GetMapping("/timeout")
    public String timeout(Integer timeout) {
        return myService.retry(timeout);
    }

    @GetMapping("/cache")
    public Friend cache(String name) {
        @Cleanup HystrixRequestContext context =
                HystrixRequestContext.initializeContext();

        Friend friend;
        for (int i = 0; i < 10; i++) {
            friend = requestCacheService.requestCache(name);
        }
        name += "???";
        friend = requestCacheService.requestCache(name);
        return friend;
    }
}
