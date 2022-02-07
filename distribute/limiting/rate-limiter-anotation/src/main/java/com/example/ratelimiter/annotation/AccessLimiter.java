package com.example.ratelimiter.annotation;

import java.lang.annotation.*;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/24 上午11:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimiter {

    /** 限流的大小 */
    int limit();
    
    /** 限流的key, 默认可以为空, 自动根据方法签名生成一个 */
    String methodKey() default "";
}
