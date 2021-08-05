package com.jhmarryme.demo.common.annotation;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;


/**
 * 自定义返回结果 包装处理
 * @author Jiahao Wang
 * @date 2021/3/4 11:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ResponseBody
public @interface ResponseResultBody {

}