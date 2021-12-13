package com.example;

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

    @GetMapping("/fallback")
    public String fallback() {
        return myService.error();
    }
}
