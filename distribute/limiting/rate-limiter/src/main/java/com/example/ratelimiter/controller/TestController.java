package com.example.ratelimiter.controller;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/17 下午9:00
 */
@SuppressWarnings("UnstableApiUsage")
@RestController
@Slf4j
public class TestController {

    /** 每秒钟可以创建两个令牌 */
    private final RateLimiter limiter = RateLimiter.create(2.0);

    /** 非阻塞限流 */
    @GetMapping("/tryAcquire")
    public String tryAcquire(Integer count) {
        // count 每次消耗的令牌
        if (limiter.tryAcquire(count)) {
            log.info("成功，允许通过，速率为{}", limiter.getRate());
            return "success";
        } else {
            log.info("错误，不允许通过，速率为{}", limiter.getRate());
            return "fail";
        }
    }

    /** 限定时间的非阻塞限流 */
    @GetMapping("/tryAcquireWithTimeout")
    public String tryAcquireWithTimeout(Integer count, Integer timeout) {
        // count 每次消耗的令牌  timeout 超时等待的时间
        if (limiter.tryAcquire(count, timeout, TimeUnit.SECONDS)) {
            log.info("成功，允许通过，速率为{}", limiter.getRate());
            return "success";
        } else {
            log.info("错误，不允许通过，速率为{}", limiter.getRate());
            return "fail";
        }
    }

    /** 同步阻塞限流 */
    @GetMapping("/acquire")
    public String acquire(Integer count) {
        limiter.acquire(count);
        log.info("成功，允许通过，速率为{}", limiter.getRate());
        return "success";
    }


    // Nginx专用
    // 1. 修改host文件 -> www.rate-limiter-test.com = localhost 127.0.0.1
    //    (127.0.0.1	www.rate-limiter-test.com)
    // 2. 修改nginx -> 将步骤1中的域名，添加到路由规则当中
    //    配置文件地址： /opt/nginx/conf/nginx.conf
    // 3. 添加配置项：参考resources文件夹下面的nginx.conf
    //
    // 重新加载nginx(Nginx处于启动) => sudo /opt/nginx/sbin/nginx -s reload
    @GetMapping("/nginx")
    public String nginx() {
        log.info("Nginx success");
        return "success";
    }

    @GetMapping("/nginx-conn")
    public String nginxConn(@RequestParam(defaultValue = "0") int secs) {
        try {
            Thread.sleep(1000L * secs);
        } catch (Exception ignored) {
        }
        return "success";
    }
}
