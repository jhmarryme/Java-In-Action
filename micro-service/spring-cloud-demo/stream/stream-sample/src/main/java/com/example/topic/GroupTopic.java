package com.example.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 * @author JiaHao Wang
 * @date 2022/1/11 下午6:56
 */
public interface GroupTopic {

    /** stream中input指接收消息端 */
    String INPUT = "group-consumer";

    /** output是生产发送消息端 */
    String OUTPUT = "group-producer";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
