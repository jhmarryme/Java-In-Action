package com.jhmarryme.rabbit.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * RabbitMessageConverter
 * @author JiaHao Wang
 * @date 2021/9/27 下午1:10
 */
public class RabbitMessageConverter implements MessageConverter {

    private final GenericMessageConverter delegateConverter;

    public RabbitMessageConverter(GenericMessageConverter delegateConverter) {
        Preconditions.checkNotNull(delegateConverter);
        this.delegateConverter = delegateConverter;
    }

    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        com.jhmarryme.rabbit.api.Message message = (com.jhmarryme.rabbit.api.Message) o;
        // 设置延迟参数
        messageProperties.setDelay(message.getDelayMills());
        return this.delegateConverter.toMessage(message, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return this.delegateConverter.fromMessage(message);
    }
}
