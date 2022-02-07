package com.example;

import com.example.hystrix.FallBack;
import com.example.springcloud.IService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/13 下午12:32
 */
@FeignClient(name = "feign-client", fallback = FallBack.class, contextId = "MyService", path = "/my-feign-client")
public interface MyService extends IService {
}
