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

    /**
     * 手动消费消息
     *
     * @param record 消息题
     * @param acknowledgment ack相关
     * @param consumer consumer信息
     */
    @KafkaListener(groupId = "group3", topics = "topic03")
    public void onMessages(ConsumerRecord<String, Object> record, Acknowledgment acknowledgment, Consumer<?, ?> consumer) {
        log.info("消费端消费消息：{}", record.value());

        // 手工签收消息
        acknowledgment.acknowledge();
    }
}
