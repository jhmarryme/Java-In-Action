package com.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import zipkin.server.internal.EnableZipkinServer;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/31 下午1:24
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZipkinServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
