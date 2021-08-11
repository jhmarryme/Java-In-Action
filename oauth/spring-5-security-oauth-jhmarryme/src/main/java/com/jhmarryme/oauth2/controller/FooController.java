package com.jhmarryme.oauth2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 * @date 2021/8/10 19:14
 */
@RestController()
public class FooController {

    @GetMapping("/foo")
    public String foo() {
        return "success";
    }
}
