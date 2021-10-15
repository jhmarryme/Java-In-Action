package com.jhmarryme.rabbit.producer.broker;

import com.jhmarryme.rabbit.api.Message;

/**
 * 具体发送不同种类型消息的接口
 * @author JiaHao Wang
 * @date 2021/9/26 下午1:05
 */
public interface RabbitBroker {

    /**
     * 迅速消息
     *
     * @param message 消息内容体
     */
    void rapidSend(Message message);

    /**
     * 确认消息
     *
     * @param message 消息内容体
     */
    void confirmSend(Message message);

    /**
     * 可靠消息
     *
     * @param message 消息内容体
     */
    void reliantSend(Message message);

    void sendMessages();
}
