package com.jhmarryme.demo.common.base.exception;

import com.jhmarryme.demo.common.base.interfaces.IResponseEnum;
import com.jhmarryme.demo.common.base.enums.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 基础异常类
 * @author Jiahao Wang
 * @date 2021/3/3 11:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException implements Serializable {
    /**
     * 错误信息
     */
    private IResponseEnum responseEnum;

    /**
     * 参数用来补充说明异常消息，如需提示用户在某IP处登录可以设置消息
     */
    private String[] params;

    /**
     * 实际数据
     */
    private Object data;

    public BaseException() {
        this(null, null, (String[]) null);
    }

    public BaseException(IResponseEnum responseEnum, String... params) {
        this(responseEnum, null, params);
    }

    public BaseException(IResponseEnum responseEnum, Object data, String... params) {
        if (responseEnum == null) {
            responseEnum = ResponseEnum.UNKNOWN_ERROR;
        }
        this.responseEnum = responseEnum;
        this.data = data;
        this.params = params;
    }

}
