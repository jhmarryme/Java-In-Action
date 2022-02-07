package com.example.rabbit.consumer.component;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceive {

    /**
     * 	组合使用监听
     *    @RabbitListener @QueueBinding @Queue @Exchange
     * @param message
     * @param channel
     * @throws Exception
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    // 声明绑定的队列和队列信息
                    value = @Queue(value = "queue-1", durable = "true"),
                    // 交换机信息
                    exchange = @Exchange(name = "exchange-1",
                            durable = "true",
                            type = "topic",
                            // 忽略声明异常
                            ignoreDeclarationExceptions = "true"),
                    // routingKey: 这里由于是 topic 模式，所以支持通配符
                    key = "springboot.*"
            )
    )
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception {
        // 1. 收到消息以后进行业务端消费处理
        System.err.println("-----------------------");
        System.err.println("消费消息:" + message.getPayload());

        // 2. 处理成功之后 获取deliveryTag 并进行手工的ACK操作, 因为我们配置文件里配置的是 手工签收
        // spring.rabbitmq.listener.simple.acknowledge-mode=manual
        // 消息到达 MQ 之后，会给每一条消息一个唯一的 deliveryTag
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);
    }

}
