package com.jhmarryme.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.jhmarryme.rabbit.api.Message;
import com.jhmarryme.rabbit.api.MessageType;
import com.jhmarryme.rabbit.common.convert.GenericMessageConverter;
import com.jhmarryme.rabbit.common.convert.RabbitMessageConverter;
import com.jhmarryme.rabbit.common.serializer.JacksonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * RabbitTemplateContainer池化封装
 * <p>每一个topic 对应一个RabbitTemplate</p>
 * <ul>
 *     <li>提高发送的效率</li>
 *     <li>可以根据不同的需求制定化不同的RabbitTemplate, 比如每一个topic 都有自己的routingKey规则</li>
 * </ul>
 * @author JiaHao Wang
 * @date 2021/9/27 下午12:27
 */
@Slf4j
@Component
public class RabbitTemplateContainer {

    /** mqTemplate 连接池 */
    private final Map<String, RabbitTemplate> templateMap = Maps.newConcurrentMap();

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private CustomConfirmCallback customConfirmCallback;

    public RabbitTemplate getTemplate(Message message) {
        Preconditions.checkNotNull(message);

        String topic = message.getTopic();
        // 尝试从现有的template池中获取
        RabbitTemplate rabbitTemplate = templateMap.get(topic);
        if (rabbitTemplate != null) {
            return rabbitTemplate;
        }

        log.info("#RabbitTemplateContainer.getTemplate# topic: {} is not exists, create one", topic);

        RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
        newTemplate.setExchange(topic);
        newTemplate.setRoutingKey(message.getRoutingKey());
        newTemplate.setRetryTemplate(new RetryTemplate());

        // 添加序列化反序列化和converter对象
        JacksonSerializer serializer = JacksonSerializer.createParametricType(Message.class);
        GenericMessageConverter gmc = new GenericMessageConverter(serializer);
        RabbitMessageConverter rmc = new RabbitMessageConverter(gmc);
        newTemplate.setMessageConverter(rmc);

        MessageType messageType = message.getMessageType();
        // 迅速消息以外的都需要设置确认回调
        if (MessageType.RAPID != messageType) {
            newTemplate.setConfirmCallback(customConfirmCallback);
        }
        templateMap.putIfAbsent(topic, newTemplate);
        return templateMap.get(topic);
    }

}
