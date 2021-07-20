package com.jhmarryme.ddd.bank.messaging;

import com.jhmarryme.ddd.bank.domain.types.AuditMessage;
import com.jhmarryme.ddd.bank.common.SendResult;
import org.springframework.beans.factory.annotation.Autowired;


public class AuditMessageProducerImpl implements AuditMessageProducer {

    private static final String TOPIC_AUDIT_LOG = "TOPIC_AUDIT_LOG";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public SendResult send(AuditMessage message) {
        String messageBody = message.serialize();
        kafkaTemplate.send(TOPIC_AUDIT_LOG, messageBody);
        return SendResult.success();
    }

    private class KafkaTemplate<T, T1> {
        public void send(T1 topicAuditLog, T1 messageBody) {
        }
    }
}