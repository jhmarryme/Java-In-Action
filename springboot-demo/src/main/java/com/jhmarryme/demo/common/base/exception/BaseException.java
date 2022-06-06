package com.jhmarryme.demo.common.base.exception;

import com.jhmarryme.demo.common.base.enums.ResponseEnum;
import com.jhmarryme.demo.common.base.interfaces.IResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 基础异常类
 * @author Jiahao Wang
 * @date 2021/3/3 11:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class BaseException extends RuntimeException implements Serializable {
    /**
     * 错误信息
     */
    private IResponseEnum responseEnum;

    /**
     * 参数用来补充说明异常消息，如需提示用户在某IP处登录可以设置消息
     */
    private Object[] args;

    public BaseException(IResponseEnum responseEnum, String message) {
        this(responseEnum, null, message, null);
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String message) {
        this(responseEnum, args, message, null);
    }

    public BaseException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(message, cause);
        if (responseEnum == null) {
            responseEnum = ResponseEnum.UNKNOWN_ERROR;
        }
        this.responseEnum = responseEnum;
        this.args = args;
    }
}
