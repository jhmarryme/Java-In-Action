package com.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/29 下午3:28
 */
@SpringBootApplication
public class RedissonApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(RedissonApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
