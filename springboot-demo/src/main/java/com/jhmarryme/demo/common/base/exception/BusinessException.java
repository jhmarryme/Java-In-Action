package com.jhmarryme.demo.common.base.exception;

import com.jhmarryme.demo.common.base.enums.ResponseEnum;
import com.jhmarryme.demo.common.base.interfaces.IResponseEnum;

import java.io.Serial;

/**
 * 通用异常类
 * @author JiaHao Wang
 * @date 3/9/21 1:57 AM
 */
public class BusinessException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BusinessException() {
        super(ResponseEnum.UNKNOWN_ERROR, ResponseEnum.UNKNOWN_ERROR.getMessage());
    }

    public BusinessException(IResponseEnum responseEnum) {
        super(responseEnum, responseEnum.getMessage());
    }

    public BusinessException(IResponseEnum responseEnum, String message) {
        super(responseEnum, message);
    }

    public BusinessException(IResponseEnum responseEnum, Object[] args, String message) {
        super(responseEnum, args, message);
    }

    public BusinessException(IResponseEnum responseEnum, Object[] args, String message, Throwable cause) {
        super(responseEnum, args, message, cause);
    }

}
