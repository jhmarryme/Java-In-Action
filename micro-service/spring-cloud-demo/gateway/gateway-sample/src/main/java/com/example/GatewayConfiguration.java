package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.time.ZonedDateTime;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/28 下午12:08
 */
@Configuration
public class GatewayConfiguration {

    @Autowired
    private TimerFilter timerFilter;

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        "java-feignclient",
                        r -> r.path("/java/my-feign-client/**")
                                .and().method(HttpMethod.GET)
                                .and().header("name")
                                .filters(f -> f.stripPrefix(1)
                                        .addResponseHeader("X-CustomerHeader", "xxx")
                                        .filters(authFilter)
                                        .filter(timerFilter))
                                .uri("lb://feign-client"))
                .route(
                        "java-auth-service",
                        r -> r.path("/java/auth/**")
                                .filters(f -> f.stripPrefix(2))
                                .uri("lb://auth-service")
                )
                .route(r -> r.path("/seckill/**")
                        .and().after(ZonedDateTime.now().plusMinutes(1))
//                        .and().before()
//                        .and().between()
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://FEIGN-CLIENT"))
                .build();
    }
}
