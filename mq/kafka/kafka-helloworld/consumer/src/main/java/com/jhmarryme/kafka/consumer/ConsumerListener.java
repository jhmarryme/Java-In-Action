package com.jhmarryme.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 *
 * @author JiaHao Wang
 * @date 2021/10/11 下午1:00
 */
@Component
@Slf4j
public class ConsumerListener {


    /** 创建3个consumer, 所属同1个group */
    /**
     * 手动消费消息
     *
     * @param record 消息题
     * @param acknowledgment ack相关
     * @param consumer consumer信息
     */
    @KafkaListener(groupId = "group2", topics = "topic2")
    public void onMessagesA(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        log.info("消费端A消费消息：{}", record.value());

        // 手工签收消息
        acknowledgment.acknowledge();
    }

    @KafkaListener(groupId = "group2", topics = "topic1")
    public void onMessagesB(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        log.info("消费端B消费消息：{}", record.value());

        // 手工签收消息
        acknowledgment.acknowledge();
    }

    @KafkaListener(groupId = "group2", topics = "topic1")
    public void onMessagesC(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        log.info("消费端C消费消息：{}", record.value());

        // 手工签收消息
        acknowledgment.acknowledge();
    }


    /** 创建3个consumer, 所属不同的group */
    @KafkaListener(groupId = "group1", topics = "topic1")
    public void onMessagesGroup1A(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        log.info("组1,消费端A消费消息：{}", record.value());
        acknowledgment.acknowledge();
    }

    @KafkaListener(groupId = "group2", topics = "topic1")
    public void onMessagesGroup2A(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        log.info("组2,消费端A消费消息：{}", record.value());
        acknowledgment.acknowledge();
    }

    @KafkaListener(groupId = "group2", topics = "topic1")
    public void onMessagesGroup2B(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        log.info("组2,消费端B消费消息：{}", record.value());
        acknowledgment.acknowledge();
    }
}
