package com.jhmarryme.rabbit.api;

import java.util.List;

/**
 *
 * @author JiaHao Wang
 * @date 2021/9/26 下午12:58
 */
public interface MessageProducer {

    /**
     * send消息的发送
     *
     * @param message 消息内容
     */
    void send(Message message);

    /**
     * 批量发送消息
     *
     * @param messages 消息内容List
     */
    void send(List<Message> messages);
}
