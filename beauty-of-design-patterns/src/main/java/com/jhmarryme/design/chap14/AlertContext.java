package com.jhmarryme.design.chap14;

import com.jhmarryme.design.chap14.handler.ErrorAlertHandler;
import com.jhmarryme.design.chap14.handler.TimeoutAlertHandler;
import com.jhmarryme.design.chap14.handler.TpsAlertHandler;
import lombok.Getter;

/**
 * ApplicationContext 是一个单例类，负责 Alert 的创建、组装（alertRule 和 notification 的依赖注入）、初始化（添加 handlers）工作
 */
public class AlertContext {

    @Getter
    private Alert alert;
    /** 饿汉式单例 */
    public static final AlertContext ALERT_CONTEXT = new AlertContext();

    public void initializeBeans() {
        //省略一些初始化代码
        AlertRule alertRule = new AlertRule();
        //省略一些初始化代码
        Notification notification = new Notification();

        alert = new Alert();
        alert.addAlertHandler(new ErrorAlertHandler(alertRule, notification));
        alert.addAlertHandler(new TpsAlertHandler(alertRule, notification));
        alert.addAlertHandler(new TimeoutAlertHandler(alertRule, notification));
    }

    private AlertContext() {
        this.initializeBeans();
    }

    public static AlertContext getInstance() {
        return ALERT_CONTEXT;
    }
}
