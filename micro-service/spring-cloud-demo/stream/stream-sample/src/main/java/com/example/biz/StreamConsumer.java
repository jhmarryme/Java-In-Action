package com.example.biz;

import com.example.entity.MessageBean;
import com.example.topic.DelayedTopic;
import com.example.topic.GroupTopic;
import com.example.topic.MyTopic;
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
                Sink.class,
                MyTopic.class,
                GroupTopic.class,
                DelayedTopic.class
        }
)
public class StreamConsumer {

    /** 这里先使用stream给的默认topic */
    @StreamListener(Sink.INPUT)
    public void consume(Object payload) {
        log.info("message consumed successfully, payload={}", payload);
    }

    /** 自定义消息广播 */
    @StreamListener(MyTopic.INPUT)
    public void consumeMyTopic(Object payload) {
        log.info("MyTopic message consumed successfully, payload={}", payload);
    }

    /** 消息分组 & 消费分区示例 */
    @StreamListener(GroupTopic.INPUT)
    public void consumeGroupTopic(Object payload) {
        log.info("GroupTopic message consumed successfully, payload={}", payload);
    }

    /** 延迟消息示例 */
    @StreamListener(DelayedTopic.INPUT)
    public void consumeDelayedTopic(MessageBean bean) {
        log.info("DelayedTopic message consumed successfully, payload={}", bean.getPayload());
    }

}