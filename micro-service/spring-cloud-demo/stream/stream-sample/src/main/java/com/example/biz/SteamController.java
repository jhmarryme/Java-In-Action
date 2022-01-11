package com.example.biz;

import com.example.topic.GroupTopic;
import com.example.topic.MyTopic;
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
public class SteamController {

    /** 由stream给我完成注入和绑定了 */
    @Autowired
    private MyTopic producer;

    @Autowired
    private GroupTopic groupTopicProducer;

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
}
