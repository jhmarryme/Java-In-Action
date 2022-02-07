package com.example.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/2 上午9:10
 */
@Slf4j
@RestController
public class Controller implements IService{

    @Value("${server.port}")
    private String port;

    @Override
    public String sayHi() {
        return "This is " + port;
    }

    @Override
    public Friend sayHiPost(@RequestBody Friend friend) {
        log.info("You are " + friend.getName());
        friend.setPort(port);
        return friend;
    }

    @Override
    @HystrixCommand(commandKey = "retryKey", fallbackMethod = "retryFallback")
    public String retry(@RequestParam(name = "timeout") int timeout) {
        while (--timeout >= 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
        log.info("retry " + port);
        return port;
    }

    @Override
    @HystrixCommand(fallbackMethod = "errorFallback")
    public String error() {
        throw new RuntimeException("black sheep");
    }

    public String errorFallback() {
        log.info("服务端降级响应errorFallback");

        return "服务端降级响应errorFallback";
    }

    public String retryFallback(int timeout) {
        log.info("服务端降级响应retryFallback");
        return "服务端降级响应retryFallback";
    }

}
