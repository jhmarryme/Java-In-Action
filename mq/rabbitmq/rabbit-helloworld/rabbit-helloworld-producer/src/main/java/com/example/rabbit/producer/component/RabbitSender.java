package com.example.rabbit.producer.component;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;


@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 	这里就是确认消息的回调监听接口，用于确认消息是否被broker所收到
     */
    final ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        /**
         * @param correlationData 作为一个唯一的标识
         * @param ack broker 是否落盘成功
         * @param cause 失败的一些异常信息
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("消息ACK结果:" + ack + ", correlationData: " + correlationData.getId());
        }
    };

    /**
     * 	对外发送消息的方法
     * @param message    具体的消息内容
     * @param properties    额外的附加属性
     * @throws Exception e
     */
    public void send(Object message, Map<String, Object> properties) throws Exception {
        // 创建消息头
        MessageHeaders mhs = new MessageHeaders(properties);
        // 创建消息
        Message<?> msg = MessageBuilder.createMessage(message, mhs);

        // 设置  生产者回调，注意这里：不是每次都需要设置
        // 里面源码逻辑是：回调对象为 null 或者 此次设置的回调对象必须是上一次的回调对象 才会成功，否则会抛出异常
        rabbitTemplate.setConfirmCallback(confirmCallback);

        // 	指定业务唯一的iD
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        // 对消息做一些处理
        MessagePostProcessor mpp = message1 -> {
            System.err.println("---> post to do: " + message1);
            // message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message1;
        };

        rabbitTemplate.convertAndSend(
                // 发往的 exchange
                "exchange-1",
                // // routingKey
                "springboot.rabbit",
                // 消息体
                msg,
                // 对消息可以做一些处理
                mpp,
                // 指定业务唯一的iD
                correlationData);

    }

}
