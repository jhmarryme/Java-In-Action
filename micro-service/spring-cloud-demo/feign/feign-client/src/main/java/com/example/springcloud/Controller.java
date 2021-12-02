package com.example.springcloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
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
}
