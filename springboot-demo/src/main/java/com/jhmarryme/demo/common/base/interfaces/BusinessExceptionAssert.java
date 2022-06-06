package com.jhmarryme.demo.common.base.interfaces;

import com.jhmarryme.demo.common.base.exception.BaseException;
import com.jhmarryme.demo.common.base.exception.BusinessException;

import java.text.MessageFormat;

/**
 *
 * @author JiaHao Wang
 * @date 2021/3/8 17:18
 */
public interface BusinessExceptionAssert extends IResponseEnum, Assert {

    @Override
    default BaseException newException() {
        return new BusinessException(this);
    }

    @Override
    default BaseException newException(Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg);
    }

    @Override
    default BaseException newException(Throwable t, Object... args) {
        String msg = MessageFormat.format(this.getMessage(), args);
        return new BusinessException(this, args, msg, t);
    }
}
