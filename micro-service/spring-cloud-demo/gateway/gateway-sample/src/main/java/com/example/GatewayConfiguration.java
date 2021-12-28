package com.example;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/28 下午12:08
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        "java-feignclient",
                        r -> r.path("/java/my-feign-client/**")
                                .and().method(HttpMethod.GET)
                                .and().header("name")
                                .filters(f -> f.stripPrefix(1)
                                        .addResponseHeader("X-CustomerHeader", "xxx"))
                                .uri("lb://feign-client"))
                .build();
    }
}
