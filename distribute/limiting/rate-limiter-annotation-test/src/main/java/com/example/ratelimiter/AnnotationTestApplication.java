package com.example.ratelimiter;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/24 上午10:52
 */
@SpringBootApplication
public class AnnotationTestApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AnnotationTestApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
