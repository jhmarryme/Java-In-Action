package com.example.biz;

import com.example.entity.MessageBean;
import com.example.topic.DelayedTopic;
import com.example.topic.ErrorTopic;
import com.example.topic.GroupTopic;
import com.example.topic.MyTopic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 * @date 2022/1/11 下午4:43
 */
@RestController
@Slf4j
public class SteamController {

    /** 由stream给我完成注入和绑定了 */
    @Autowired
    private MyTopic producer;

    @Autowired
    private GroupTopic groupTopicProducer;

    @Autowired
    private DelayedTopic delayedTopicProducer;

    @Autowired
    private ErrorTopic errorTopic;

    @PostMapping("/send")
    public String send(@RequestParam("body") String body) {
        // 生产一条消息
        producer.output().send(MessageBuilder.withPayload(body).build());
        return null;
    }

    @PostMapping("/send-group")
    public String sendGroup(@RequestParam("body") String body) {
        groupTopicProducer.output().send(MessageBuilder.withPayload(body).build());
        return null;
    }

    @PostMapping("/send-dm")
    public String sendDm(
            @RequestParam("body") String body,
            @RequestParam("seconds") Integer seconds) {
        MessageBean messageBean = MessageBean.builder().payload(body).build();
        log.info("ready to send delayed message");
        delayedTopicProducer.output().send(
                MessageBuilder.withPayload(messageBean)
                        .setHeader("x-delay", seconds * 1000)
                        .build());
        return null;
    }

    @PostMapping("/send-error")
    public String sendError(@RequestParam("body") String body) {
        errorTopic.output().send(MessageBuilder.withPayload(body).build());
        return null;
    }

}
