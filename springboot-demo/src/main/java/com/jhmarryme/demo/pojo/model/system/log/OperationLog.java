package com.jhmarryme.demo.pojo.model.system.log;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "operation_log")
public class OperationLog {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 操作模块
     */
    private String module;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 操作描述
     */
    private String description;

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
     * 操作方法
     */
    private String method;

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
     * 请求类型
     */
    @Column(name = "req_type")
    private String reqType;

    /**
     * 请求参数
     */
    @Column(name = "req_param")
    private String reqParam;

    /**
     * 返回参数
     */
    @Column(name = "res_param")
    private String resParam;

    /**
     * 请求体
     */
    @Column(name = "req_body")
    private String reqBody;
}