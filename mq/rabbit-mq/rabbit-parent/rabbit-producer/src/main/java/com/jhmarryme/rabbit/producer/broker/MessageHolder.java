package com.jhmarryme.rabbit.producer.broker;

import com.google.common.collect.Lists;
import com.jhmarryme.rabbit.api.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程独有变量， 用于存放批量消息
 * @author JiaHao Wang
 * @date 2021/9/28 上午9:39
 */
public class MessageHolder {

    /** 存放批量消息的List */
    private final List<Message> messages = new ArrayList<>();

    /** 将当前对象存入ThreadLocal */
    public static final ThreadLocal<MessageHolder> HOLDER = ThreadLocal.withInitial(MessageHolder::new);

    public static void add(Message message) {
        HOLDER.get().messages.add(message);
    }

    /**
     * 返回messages中所有内容， 并清空messages
     *
     * @author Jiahao Wang
     * @return java.util.List<com.jhmarryme.rabbit.api.Message>
     */
    public static List<Message> clear() {
        List<Message> tmpMessages = Lists.newArrayList(HOLDER.get().messages);
        HOLDER.remove();
        return tmpMessages;
    }

}
