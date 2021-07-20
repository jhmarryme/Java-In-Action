package com.jhmarryme.ddd.bank.messaging;

import com.jhmarryme.ddd.bank.domain.types.AuditMessage;
import com.jhmarryme.ddd.bank.common.SendResult;


/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:57
 */
public interface AuditMessageProducer {
    SendResult send(AuditMessage message);
}
