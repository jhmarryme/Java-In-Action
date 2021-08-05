package com.jhmarryme.demo.config.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhmarryme.demo.common.annotation.ResponseResultBody;
import com.jhmarryme.demo.common.base.CommonResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

/**
 * 全局处理 包装返回结果
 *      <p>使用@ResponseBody注解会把返回Object序列化成JSON字符串,
 *      <p>所以 在序列化前把Object赋值给CommonResult<Object>
 * @author JiaHao Wang
 * @date 2021/3/3 10:40
 */
@Slf4j
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final Class<? extends Annotation> ANNOTATION_TYPE = ResponseResultBody.class;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 判断类或者方法是否使用了 @ResponseResultBody
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), ANNOTATION_TYPE) ||
                methodParameter.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    /**
     * 当类或者方法使用了 @ResponseResultBody 就会调用这个方法
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // 防止重复包裹的问题出现
        if (body instanceof CommonResult) {
            return body;
        }
        // 直接返回string类型有异常
        if (body instanceof String) {
            return objectMapper.writeValueAsString(CommonResult.success(body));
        }
        return CommonResult.success(body);
    }

}