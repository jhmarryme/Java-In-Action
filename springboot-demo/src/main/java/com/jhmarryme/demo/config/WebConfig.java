package com.jhmarryme.demo.config;

import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * web配置
 * @author Jiahao Wang
 * @date 2021/3/5 11:22
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * DateTime格式化字符串
     */
    private static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Date格式化字符串
     */
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * Time格式化字符串
     */
    private static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";

    @Autowired
    private MessageSource messageSource;

    @Override
    public Validator getValidator() {
        return validator();
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        //该行代码是给Hibernate-Validator框架设置一些参数，比如下面一行设置校验快速失败模式
//        validator.getValidationPropertyMap().put(HibernateValidatorConfiguration.FAIL_FAST, "true");
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    /**
     * 拦截请求中 key 为 lang 的参数（不配置的话是 locale），这个参数则指定了当前的环境信息。
     *
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // 根据请求头中的lang区别, zh-TW/zh-CH/en-US
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);
    }

    /**
     * 替换掉默认的 AcceptHeaderLocaleResolver
     *      不同于 AcceptHeaderLocaleResolver 通过请求头来判断当前的环境信息，
     *      SessionLocaleResolver 将客户端的 Locale 保存到 HttpSession对象中，并且可以进行修改
     *     （这意味着当前环境信息，前端给浏览器发送一次即可记住，只要 session 有效，浏览器就不必再次告诉服务端当前的环境信息）。
     * @return org.springframework.web.servlet.LocaleResolver
     */
    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        // 默认为中文
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return localeResolver;
    }

    /**
     * Jackson序列化和反序列化转换器，用于转换Post请求体中的json以及将对象序列化为返回响应的json
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)))
                .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)))
                .serializerByType(Date.class, new DateSerializer(false, new SimpleDateFormat(DEFAULT_DATETIME_PATTERN)))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_PATTERN)))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN)))
                .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_PATTERN)))
                .deserializerByType(Date.class, new DateDeserializers.DateDeserializer(DateDeserializers.DateDeserializer.instance, new SimpleDateFormat(DEFAULT_DATETIME_PATTERN), DEFAULT_DATETIME_PATTERN))
                ;
    }
}
