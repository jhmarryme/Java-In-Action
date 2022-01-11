package com.example.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 *
 * @author JiaHao Wang
 * @date 2022/1/11 下午4:06
 */
public interface MyTopic {

    /** stream中input指接收消息端 */
    String INPUT = "myTopic-consumer";

    /** output是生产发送消息端 */
    String OUTPUT = "myTopic-producer";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();
}
