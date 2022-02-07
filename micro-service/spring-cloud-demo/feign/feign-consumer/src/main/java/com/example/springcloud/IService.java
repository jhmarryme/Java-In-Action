package com.example.springcloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/1 下午3:34
 */
@FeignClient("eureka-client")
public interface IService {

    @GetMapping("/sayHi")
    String sayHi();
}
