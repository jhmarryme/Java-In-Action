package com.example.biz;

import com.example.entity.MessageBean;
import com.example.topic.DelayedTopic;
import com.example.topic.ErrorTopic;
import com.example.topic.GroupTopic;
import com.example.topic.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import java.util.concurrent.atomic.AtomicInteger;

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
                DelayedTopic.class,
                ErrorTopic.class
        }
)
public class StreamConsumer {

    private AtomicInteger count = new AtomicInteger(1);

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


    /**  */
    @StreamListener(ErrorTopic.INPUT)
    public void consumeErrorTopic(Object payload) {

        log.info("====== 进入异常处理 ======");
        if (count.incrementAndGet() % 4 == 0) {
            log.info("====== i am fine =====");
            count.set(0);
        } else {
            log.info("====== what is you problem=====");
            throw new RuntimeException("====== what is you problem=====");
        }

        log.info("Error message consumed successfully, payload={}", payload);
    }
}