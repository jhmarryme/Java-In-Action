package com.jhmarryme.demo.pojo.model.system.log;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "exception_log")
public class ExceptionLog {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 异常名称
     */
    private String name;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 请求uri
     */
    private String uri;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 操作时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 操作版本号
     */
    private String ver;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 请求类型
     */
    @Column(name = "req_type")
    private String reqType;

    /**
     * 异常信息
     */
    private String msg;

    /**
     * 请求参数
     */
    @Column(name = "req_param")
    private String reqParam;

    /**
     * 请求体
     */
    @Column(name = "req_body")
    private String reqBody;

}