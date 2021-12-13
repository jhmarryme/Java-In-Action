package com.example.hystrix;

import com.example.MyService;
import com.example.springcloud.Friend;
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
    public String error() {
        log.info("Fallback: I'm not a black sheep any more");
        return "Fallback: I'm not a black sheep any more";
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
        return null;
    }
}
