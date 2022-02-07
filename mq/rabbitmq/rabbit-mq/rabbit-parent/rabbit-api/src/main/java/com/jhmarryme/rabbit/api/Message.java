package com.jhmarryme.rabbit.api;

import com.jhmarryme.rabbit.api.exception.MessageRunTimeException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 自定义的消息实体
 * @author JiaHao Wang
 * @date 2021/9/26 下午12:34
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Message {

    /** 消息唯一id */
    private String id;

    /** 消息的主题 */
    private String topic;

    /**    消息的路由规则	*/
    private String routingKey = "";

    /**    消息的附加属性	*/
    private Map<String, Object> attributes = new HashMap<>();

    /**    延迟消息的参数配置	*/
    private int delayMills;

    /**    消息类型：默认为confirm消息类型	*/
    private MessageType messageType = MessageType.CONFIRM;

    public void check() {
        // 1. check messageId
        if (id == null) {
            throw new MessageRunTimeException("this id is null");
        }
        // 2. topic is null
        if (topic == null) {
            throw new MessageRunTimeException("this topic is null");
        }
    }
}
