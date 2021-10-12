package com.jhmarryme.kafka.producer;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

/**
 *
 * @author JiaHao Wang
 * @date 2021/10/11 下午2:36
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class KafkaProducerServiceTest {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @SneakyThrows
    @Test
    @DisplayName("kafka生产者发送消息")
    void sendMessage() {
        String topic = "topic03";
        Stream.iterate(1, integer -> integer + 1)
                .limit(100)
                .forEach(integer -> {
                    kafkaProducerService.sendMessage(topic, "hello kafka " + integer);
                });

        Thread.sleep(Integer.MAX_VALUE);
    }
}