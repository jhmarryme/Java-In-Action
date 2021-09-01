package com.jhmarryme.design.chap14.handler;

import com.jhmarryme.design.chap14.AlertRule;
import com.jhmarryme.design.chap14.ApiStatInfo;
import com.jhmarryme.design.chap14.Notification;
import com.jhmarryme.design.chap14.NotificationEmergencyLevel;

/**
 * 接口超时处理逻辑 handler
 */
public class TimeoutAlertHandler extends AlertHandler{

    public TimeoutAlertHandler(AlertRule rule, Notification notification) {
        super(rule, notification);
    }

    @Override
    public void check(ApiStatInfo apiStatInfo) {
        long timeoutTps = apiStatInfo.getTimeoutCount() / apiStatInfo.getDurationOfSeconds();
        if (timeoutTps > rule.getMatchedRule(apiStatInfo.getApi()).getMaxTimeoutTps()) {
            notification.notify(NotificationEmergencyLevel.URGENCY, "TimeOutAlertHandler");
        }
    }
}
