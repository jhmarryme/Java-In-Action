package com.example.springcloud;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/1 下午3:34
 */
@FeignClient(name = "feign-client", path = "/my-feign-client")
public interface IService {

    @GetMapping("/sayHi")
    String sayHi();

    @PostMapping("/sayHi")
    Friend sayHiPost(@RequestBody Friend friend);

    @PostMapping("/retry")
    String retry(@RequestParam(name = "timeout") int timeout);
}
