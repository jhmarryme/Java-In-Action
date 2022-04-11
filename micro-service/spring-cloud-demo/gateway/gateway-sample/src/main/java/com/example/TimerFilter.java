package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
// GlobalFilter 全局过滤器
public class TimerFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 给接口计时并能打出很漂亮的log
        StopWatch timer = new StopWatch();
        // 开始计时
        timer.start(exchange.getRequest().getURI().getRawPath());

        // 我们还可以对调用链进行加工,手工放入请求参数
//        exchange.getAttributes().put("requestTimeBegain", System.currentTimeMillis());
        return chain.filter(exchange).then(
                //这里就是执行完过滤进行调用的地方
                Mono.fromRunnable(() -> {
                    timer.stop();
                    log.info(timer.prettyPrint());
                })
        );
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
