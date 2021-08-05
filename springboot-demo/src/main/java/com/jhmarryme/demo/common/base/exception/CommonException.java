package com.jhmarryme.demo.common.base.exception;

import com.jhmarryme.demo.common.base.interfaces.IResponseEnum;

import java.io.Serial;

/**
 * 通用异常类
 * @author JiaHao Wang
 * @date 3/9/21 1:57 AM
 */
public class CommonException extends BaseException {

    @Serial
    private static final long serialVersionUID = 1L;

    public CommonException() {
        super();
    }

    public CommonException(IResponseEnum responseEnum, String... params) {
        super(responseEnum, params);
    }

    public CommonException(IResponseEnum responseEnum, Object data, String... params) {
        super(responseEnum, data, params);
    }

}
