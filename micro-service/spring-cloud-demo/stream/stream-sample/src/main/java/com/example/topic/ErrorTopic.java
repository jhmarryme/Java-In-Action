package com.example.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 * @author JiaHao Wang
 * @date 2022/1/13 下午12:19
 */
public interface ErrorTopic {

    String INPUT = "error-consumer";

    String OUTPUT = "error-producer";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
