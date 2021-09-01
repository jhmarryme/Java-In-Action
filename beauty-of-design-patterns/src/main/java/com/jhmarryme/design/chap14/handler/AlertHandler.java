package com.jhmarryme.design.chap14.handler;

import com.jhmarryme.design.chap14.AlertRule;
import com.jhmarryme.design.chap14.ApiStatInfo;
import com.jhmarryme.design.chap14.Notification;

public abstract class AlertHandler {

    protected AlertRule rule;
    protected Notification notification;


    public AlertHandler(AlertRule rule, Notification notification) {
        this.rule = rule;
        this.notification = notification;
    }

    public abstract void check(ApiStatInfo apiStatInfo);
}
