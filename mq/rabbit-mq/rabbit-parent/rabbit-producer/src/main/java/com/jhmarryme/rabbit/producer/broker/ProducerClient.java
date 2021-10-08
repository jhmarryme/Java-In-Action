package com.jhmarryme.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.jhmarryme.rabbit.api.Message;
import com.jhmarryme.rabbit.api.MessageProducer;
import com.jhmarryme.rabbit.api.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author JiaHao Wang
 * @date 2021/9/26 下午12:57
 */
@Component
@Slf4j
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) {
        Preconditions.checkNotNull(message.getTopic());
        switch (message.getMessageType()) {
            case RAPID -> rabbitBroker.rapidSend(message);
            case CONFIRM -> rabbitBroker.confirmSend(message);
            case RELIANT -> rabbitBroker.reliantSend(message);
        }

    }

    @Override
    public void send(List<Message> messages) {
        // 将消息添加到 ThreadLocal中
        messages.forEach(message -> {
            message.setMessageType(MessageType.RAPID);
            MessageHolder.add(message);
        });
        // 批量发送
        rabbitBroker.sendMessages();
    }
}
