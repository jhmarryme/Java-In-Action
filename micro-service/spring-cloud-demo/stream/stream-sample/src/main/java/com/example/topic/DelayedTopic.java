package com.example.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 * @author JiaHao Wang
 * @date 2022/1/12 下午3:10
 */
public interface DelayedTopic {

    String INPUT = "delayed-consumer";

    String OUTPUT = "delayed-producer";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
