package com.jhmarryme.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 *
 * @author JiaHao Wang
 * @date 2021/10/11 下午1:04
 */
@Slf4j
@Component
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Object object) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, object);

        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("发送消息失败：{}", throwable.getMessage());

            }
            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                log.info("发送消息成功：{}", stringObjectSendResult.toString());

            }
        });
    }

}
