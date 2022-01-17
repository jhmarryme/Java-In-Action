package com.jhmarryme.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.jhmarryme.rabbit.api.MessageType;
import com.jhmarryme.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * 自定义的消息确认回调处理
 * @author JiaHao Wang
 * @date 2021/10/8 下午5:03
 */
@Slf4j
@Component
public class CustomConfirmCallback implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private MessageStoreService messageStoreService;

    /** 构建一个分割器，以 # 分隔 */
    private final Splitter splitter = Splitter.on("#");

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        Preconditions.checkNotNull(correlationData);
        // correlationData中的id为（消息id + 发送时间 + 消息类型）
        List<String> strings = splitter.splitToList(correlationData.getId());
        String msgId = strings.get(0);
        LocalDateTime msgSendTime =
                Instant.ofEpochMilli(Long.parseLong(strings.get(1))).atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (b) {
            // 当Broker 返回ACK成功时, 就是更新一下日志表里对应的消息发送状态为 SEND_OK
            // 如果当前消息类型为reliant 我们就去数据库查找并进行更新
            Optional<MessageType> match = MessageType.match(strings.get(2));
            match.ifPresent(type -> {
                if (MessageType.RELIANT == type) {
                    this.messageStoreService.succuess(msgId);
                }
            });
            log.info("send message is OK, confirm messageId: {}, msgSendTime: {}", msgId, msgSendTime);
        } else {
            log.error("send message is Fail, confirm messageId: {}, msgSendTime: {}", msgId, msgSendTime);
        }
    }
}
