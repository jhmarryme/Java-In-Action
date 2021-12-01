package com.example.springcloud;


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
