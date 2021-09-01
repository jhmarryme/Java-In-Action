package com.jhmarryme.design.chap14;

import lombok.extern.slf4j.Slf4j;

/**
 * Notification 是告警通知类，支持邮件、短信、微信、手机等多种通知渠道
 */
@Slf4j
public class Notification {

    public void notify(NotificationEmergencyLevel notificationEmergencyLevel, String tips) {
        log.info("收到通知: {}, tips: {}", notificationEmergencyLevel, tips);
    }
}
