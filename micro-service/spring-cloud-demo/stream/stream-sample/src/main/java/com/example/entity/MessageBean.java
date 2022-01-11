package com.example.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageBean {

    /** 生产者产生的消息体 */
    private String payload;
}