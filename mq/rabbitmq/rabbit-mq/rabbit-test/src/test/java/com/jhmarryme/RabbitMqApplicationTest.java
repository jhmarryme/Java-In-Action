package com.jhmarryme;

import com.google.common.collect.Maps;
import com.jhmarryme.rabbit.api.Message;
import com.jhmarryme.rabbit.api.MessageType;
import com.jhmarryme.rabbit.producer.broker.ProducerClient;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * rabbit mq 发送消息
 * @author JiaHao Wang
 * @date 2021/9/29 下午5:05
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RabbitMqApplicationTest {

    @Autowired
    private ProducerClient producerClient;

    @SneakyThrows
    @AfterEach
    public void sleep() {
        Thread.sleep(3000L);
    }

    @Test
    @DisplayName("连续发送10条确认消息")
    public void whenSendConfirmMsgSuccess() {
        buildMessageList(MessageType.CONFIRM, 10)
                .forEach(message -> producerClient.send(message));
    }

    @Test
    @DisplayName("连续发送10条迅速消息")
    public void whenSendRapidMsgSuccess() {
        buildMessageList(MessageType.RAPID, 10)
                .forEach(message -> producerClient.send(message));
    }

    @Test
    @DisplayName("批量发送10条迅速消息")
    public void whenBatchSendRapidMsgSuccess() {
        List<Message> messages = buildMessageList(MessageType.RAPID, 10);
        producerClient.send(messages);
    }

    @Test
    @DisplayName("发送1条可靠性消息")
    public void whenSebdReliantMsgSuccess() {
        buildMessageList(MessageType.RELIANT, 1)
                .forEach(message -> producerClient.send(message));
    }

    public List<Message> buildMessageList(MessageType messageType, int messageNum) {
        ArrayList<Message> messages = new ArrayList<>();
        Stream.iterate(1, integer -> integer + 1)
                .limit(messageNum)
                .forEach(integer -> {
                    String uuid = UUID.randomUUID().toString();
                    Map<String, Object> attributes = Maps.newHashMap();
                    attributes.put("name", "jjjjh");
                    attributes.put("age", integer);
                    Message message = Message.builder()
                            .id(uuid)
                            .topic("exchange-1")
                            .routingKey("springboot.abc")
                            .attributes(attributes)
                            .messageType(messageType)
                            .build();
                    messages.add(message);
                });
        return messages;
    }
}
