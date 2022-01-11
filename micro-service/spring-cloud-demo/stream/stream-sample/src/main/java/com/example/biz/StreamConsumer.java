package com.example.biz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 *
 * @author JiaHao Wang
 * @date 2022/1/11 上午11:12
 */
@Slf4j
@EnableBinding(
        value = {
                Sink.class
        }
)
public class StreamConsumer {

    /** 这里先使用stream给的默认topic */
    @StreamListener(Sink.INPUT)
    public void consume(Object payload) {
        log.info("message consumed successfully, payload={}", payload);
    }
}