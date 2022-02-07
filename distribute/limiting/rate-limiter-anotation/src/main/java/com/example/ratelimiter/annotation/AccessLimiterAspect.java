package com.example.ratelimiter.annotation;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/24 上午11:20
 */
@Aspect
@Component
@Slf4j
public class AccessLimiterAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisScript<Boolean> rateLimitScript;

    @Pointcut("@annotation(com.example.ratelimiter.annotation.AccessLimiter)")
    public void cut() {
    }

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        // 1. 获得方法签名，作为method Key

        // 通过反射获取切入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 拿到注解信息
        AccessLimiter annotation = method.getAnnotation(AccessLimiter.class);
        if (annotation == null) {
            return;
        }

        // 获取注解上的信息
        String methodKey = annotation.methodKey();
        int limit = annotation.limit();

        // 不存在key时, 自动生成一个
        if (methodKey == null || "".equals(methodKey)) {
            Class<?>[] types = method.getParameterTypes();
            methodKey = method.getDeclaringClass().getName() + "." + method.getName();
            String paramTypes = Arrays.stream(types)
                    .map(Class::getName)
                    .collect(Collectors.joining(","));
            methodKey += "#" + paramTypes;
            log.info("methodKey:{}", methodKey);

        }

        // 2. 调用Redis
        Boolean acquired = stringRedisTemplate.execute(
                // lua脚本
                rateLimitScript,
                // lua中的keys
                Lists.newArrayList(methodKey),
                // lua中的argv
                Integer.toString(limit)
        );

        if (Boolean.FALSE.equals(acquired)) {
            log.error("Your access is blocked, key = {}", methodKey);
            throw new RuntimeException("Your access is blocked");
        }
    }
}
