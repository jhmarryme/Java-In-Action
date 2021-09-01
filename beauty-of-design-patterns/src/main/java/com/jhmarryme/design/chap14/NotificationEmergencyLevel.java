package com.jhmarryme.design.chap14;

/**
 * NotificationEmergencyLevel 表示通知的紧急程度，不同的紧急程度对应不同的发送渠道。
 */
public enum NotificationEmergencyLevel {
    /** 紧急 */
    URGENCY,
    /** 严重 */
    SEVERE,
    /** 普 */
    NORMAL,
    /** 无关紧要 */
    TRIVIAL,
    ;
}
