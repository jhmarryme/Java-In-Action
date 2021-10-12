package com.jhmarryme.esjob;

import com.jhmarryme.rabbit.producer.broker.RabbitBroker;
import com.jhmarryme.rabbit.producer.constant.BrokerMessageStatus;
import com.jhmarryme.rabbit.producer.entity.BrokerMessage;
import com.jhmarryme.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 消息补偿机制
 * @author JiaHao Wang
 */
@Slf4j
@Service
public class RetryMessageFlowJob implements DataflowJob<BrokerMessage> {

    @Autowired
    private MessageStoreService messageStoreService;

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        // 查询所有status为SEND_FAIL， 且nextRetry小于当前时间的消息
        List<BrokerMessage> messages =
                messageStoreService.fetchTimeOutMessage4Retry(BrokerMessageStatus.SEND_FAIL);
        log.info("--------------@@@@@@@@@@ 抓取数据集合, 数量: {}--------------", messages.size());
        return messages;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> list) {
        list.forEach(brokerMessage -> {
            String messageId = brokerMessage.getMessageId();
            // 达到最大次数后不再继续发送
            if (brokerMessage.getTryCount() >= 3) {
                log.warn("--------------消息最终发送失败， 消息ID: {}--------------", messageId);
            } else {
                // 重发消息之前更新TryCount
                messageStoreService.updateTryCount(messageId);
                // 重发消息
                rabbitBroker.reliantSend(brokerMessage.getMessage());
            }
        });
    }
}
