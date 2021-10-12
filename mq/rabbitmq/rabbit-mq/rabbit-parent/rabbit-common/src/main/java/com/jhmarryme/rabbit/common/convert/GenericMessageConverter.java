package com.jhmarryme.rabbit.common.convert;

import com.google.common.base.Preconditions;
import com.jhmarryme.rabbit.common.serializer.Serializer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.lang.NonNullApi;

/**
 * 实现 {@link MessageConverter}中的序列化方法<p>
 *      完成自定义的{@link com.jhmarryme.rabbit.api.Message} -> {@link org.springframework.amqp.core.Message}
 * @author JiaHao Wang
 * @date 2021/9/27 下午12:47
 */
public class GenericMessageConverter implements MessageConverter {

    /** 序列化工具 */
    private final Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);
        this.serializer = serializer;
    }

    /**
     * o(自定义的消息) 转换为 amqp的消息
     *
     * @param o 自定义的消息， 实际类型应该为{@link com.jhmarryme.rabbit.api.Message}
     * @param messageProperties messageProperties
     * @return org.springframework.amqp.core.Message amqp的消息
     */
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(serializer.serializeRaw(o), messageProperties);
    }

    /**
     * 将amqp的byte[]消息 转为 自定义的消息
     *
     * @param message amqp的byte[]消息
     * @return java.lang.Object 自定义的消息， 实际类型应该为{@link com.jhmarryme.rabbit.api.Message}
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        // 将amqp的byte[]消息 转为 自定义的消息
        return serializer.deserialize(message.getBody());
    }
}
