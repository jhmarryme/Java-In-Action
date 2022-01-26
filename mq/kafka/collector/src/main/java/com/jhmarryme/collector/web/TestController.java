package com.jhmarryme.collector.web;

import com.jhmarryme.collector.util.InputMDC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JiaHao Wang
 * @date 2021/10/12 下午10:29
 */
@Slf4j
@RestController
public class TestController {
    /**
     * [%d{yyyy-MM-dd'T'HH:mm:ss.SSSZZ}]
     * [%level{length=5}]
     * [%thread-%tid]
     * [%logger]
     * [%X{hostName}]
     * [%X{ip}]
     * [%X{applicationName}]
     * [%F,%L,%C,%M]
     * [%m] ## '%ex'%n
     * -----------------------------------------------
     * [2019-09-18T14:42:51.451+08:00]
     * [INFO]
     * [main-1]
     * [org.springframework.boot.web.embedded.tomcat.TomcatWebServer]
     * []
     * []
     * []
     * [TomcatWebServer.java,90,org.springframework.boot.web.embedded.tomcat.TomcatWebServer,initialize]
     * [Tomcat initialized with port(s): 8001 (http)] ## ''
     *
     * ["message",
     * "\[%{NOTSPACE:currentDateTime}\]
     *  \[%{NOTSPACE:level}\]
     *  \[%{NOTSPACE:thread-id}\]
     *  \[%{NOTSPACE:class}\]
     *  \[%{DATA:hostName}\]
     *  \[%{DATA:ip}\]
     *  \[%{DATA:applicationName}\]
     *  \[%{DATA:location}\]
     *  \[%{DATA:messageInfo}\]
     *  ## (\'\'|%{QUOTEDSTRING:throwable})"]
     *
     * [%d{yyyy-MM-dd'T'HH:mm:ss.SSSZZ}]   格式化成 UTC 时间
     * [%level{length=5}] 日志级别
     * [%thread-%tid] 线程 ID
     * [%logger] 日志信息，是哪一个类打印的日志，全限定包名
     * [%X{hostName}]   X 表示自定义参数信息
     * [%X{ip}]
     * [%X{applicationName}]
     * [%F,%L,%C,%M] 类名（无具体包名）、行数、具体包名+类名、方法名
     * [%m] 表示消息的具体内容，比如：我是一条 info 级别的日志
     * ## '%ex' 这里是自定义特殊约定的，两个 ## 分隔，后面的是 $ex 是异常信息，外面携带了单引号，后面会说明它的含义
     * %n 换行符
     */
    @RequestMapping(path = "/idx", method = {RequestMethod.GET, RequestMethod.POST})
    public String idx(HttpServletRequest request, HttpServletResponse response) {

        InputMDC.putMDC();
        log.info("我是一条info日志");

        log.warn("我是一条warn日志");

        log.error("我是一条error日志");
        return "idx";
    }

    @RequestMapping(path = "/err", method = {RequestMethod.GET, RequestMethod.POST})
    public String err(HttpServletRequest request, HttpServletResponse response) {
        InputMDC.putMDC();
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            log.error("算术异常", e);
        }
        return null;
    }
}
