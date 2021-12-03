package com.example.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/2 上午9:16
 */
@RestController
public class Controller {

    @Autowired
    private IService service;

    @GetMapping("/sayHi")
    public String sayHi() {
        return service.sayHi();
    }

    @PostMapping("/sayHi")
    public Friend sayHiPost(@RequestBody Friend friend) {
        return service.sayHiPost(friend);
    }

    @GetMapping("/retry")
    public String retry(@RequestParam(name = "timeout") Integer timeout) {
        return service.retry(timeout);
    }
}
