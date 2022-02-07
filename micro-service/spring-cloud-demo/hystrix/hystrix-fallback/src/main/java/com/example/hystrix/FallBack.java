package com.example.hystrix;

import com.example.MyService;
import com.example.springcloud.Friend;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/13 下午12:32
 */
@Slf4j
@Component
public class FallBack implements MyService {

    @Override
    @HystrixCommand(fallbackMethod = "fallback2")
    public String error() {
        // log.info("Fallback: I'm not a black sheep any more");
        // return "Fallback: I'm not a black sheep any more";
        throw new RuntimeException("first fallback");
    }

    @HystrixCommand(fallbackMethod = "fallback3")
    public String fallback2() {
        log.info("fallback again");
        throw new RuntimeException("fallback again");
    }

    public String fallback3() {
        log.info("fallback again and again");
        return "success";
    }

    @Override
    public String sayHi() {
        return null;
    }

    @Override
    public Friend sayHiPost(Friend friend) {
        return null;
    }

    @Override
    public String retry(int timeout) {
        return "You are too late!";
    }
}
