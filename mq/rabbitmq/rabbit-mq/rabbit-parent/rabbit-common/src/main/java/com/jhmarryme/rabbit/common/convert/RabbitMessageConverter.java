package com.jhmarryme.rabbit.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * 扩展基础转换器，使用装饰者模式，持有基础转换器进行转换，在转换前可以做一些我们自己特有的逻辑处理
 * @author JiaHao Wang
 * @date 2021/9/27 下午1:10
 */
public class RabbitMessageConverter implements MessageConverter {

    /** 在基础转换器上做装饰 */
    private final GenericMessageConverter delegateConverter;

    public RabbitMessageConverter(GenericMessageConverter delegateConverter) {
        Preconditions.checkNotNull(delegateConverter);
        this.delegateConverter = delegateConverter;
    }

    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        com.jhmarryme.rabbit.api.Message message = (com.jhmarryme.rabbit.api.Message) o;
        // 在写出前，定制自己的逻辑
        // 比如，可以设置消息过期时间、设置成 utf8 格式等，messageProperties 中有很多 rabbit 相关的东西可以set
        // messageProperties.setExpiration(String.valueOf(1000 * 60));

        // 设置延迟参数
        messageProperties.setDelay(message.getDelayMills());
        return this.delegateConverter.toMessage(message, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return this.delegateConverter.fromMessage(message);
    }
}
