package com.jhmarryme.rabbit.producer.broker;

import com.jhmarryme.rabbit.api.Message;
import com.jhmarryme.rabbit.api.MessageType;
import com.jhmarryme.rabbit.producer.constant.BrokerMessageConst;
import com.jhmarryme.rabbit.producer.constant.BrokerMessageStatus;
import com.jhmarryme.rabbit.producer.entity.BrokerMessage;
import com.jhmarryme.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 真正的发送不同类型的消息实现类
 * @author JiaHao Wang
 * @date 2021/9/26 下午1:07
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    private MessageStoreService messageStoreService;

    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    @Override
    public void reliantSend(Message message) {
        message.setMessageType(MessageType.RELIANT);
        BrokerMessage bm = messageStoreService.selectByMessageId(message.getId());
        // 消息如果已经入库， 则不需要处理
        if (bm == null) {
            BrokerMessage brokerMessage = new BrokerMessage();
            Date now = new Date();
            brokerMessage.setMessageId(message.getId());
            brokerMessage.setMessage(message);
            brokerMessage.setStatus(BrokerMessageStatus.SENDING.getCode());
            brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
            brokerMessage.setCreateTime(now);
            brokerMessage.setUpdateTime(now);
            // 1. 发送前的消息入库
            messageStoreService.insert(brokerMessage);
        }
        // 2. 发送消息
        sendKernel(message);
    }

    @Override
    public void sendMessages() {
        List<Message> messages = MessageHolder.clear();
        messages.forEach(message -> MessageHolderAsyncBaseQueue.submit(() -> {
            submitMessage(message);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getId());

        }));
    }

    /**
     * 发送消息的核心方法 使用异步线程池进行发送消息
     *
     * @author Jiahao Wang
     * @date 2021/9/27 下午12:22
     * @param message 消息实体
     */
    private void sendKernel(Message message) {
        AsyncBaseQueue.submit(() -> {
            submitMessage(message);
            log.info("#RabbitBrokerImpl.send messages# send to rabbitmq, messageId: {}", message.getId());
        });
    }

    private void submitMessage(Message message) {
        // 1.1 生成唯一ID（id + 系统时间+ 消息类型）
        CorrelationData correlationData = new CorrelationData(
                String.format("%s#%s#%s",
                        message.getId(),
                        System.currentTimeMillis(),
                        message.getMessageType().getType())
        );
        // 1.2 topic
        String topic = message.getTopic();
        // 1.3 routingKey
        String routingKey = message.getRoutingKey();

        // 2. 从连接池取出rabbitTemplate
        RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
        // 3. 发送消息
        rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
    }

}
