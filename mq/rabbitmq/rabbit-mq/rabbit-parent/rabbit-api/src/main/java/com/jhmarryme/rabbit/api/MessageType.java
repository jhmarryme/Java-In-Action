package com.jhmarryme.rabbit.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 *
 * @author JiaHao Wang
 * @date 2021/9/26 下午12:35
 */
@AllArgsConstructor
public enum MessageType {
    /** 迅速消息：不需要保障消息的可靠性, 也不需要做confirm确认 */
    RAPID("0"),
    /** 确认消息：不需要保障消息的可靠性，但是会做消息的confirm确认 */
    CONFIRM("1"),
    /** 可靠性消息： 一定要保障消息的100%可靠性投递，不允许有任何消息的丢失
     * PS: 保障数据库和所发的消息是原子性的（最终一致的）
     */
    RELIANT("2"),
    ;

    @Getter
    private String type;

    public static Optional<MessageType> match(String type) {
        for (MessageType value : values()) {
            if (value.getType().equals(type)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
