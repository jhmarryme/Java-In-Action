package com.jhmarryme.demo.common.base.interfaces;

import com.jhmarryme.demo.common.base.exception.BaseException;

/**
 *
 * @author JiaHao Wang
 * @date 2021/3/8 17:05
 */
public interface Assert {


    /**
     * 创建异常
     *
     * @return com.jhmarryme.demo.common.base.exception.BaseException
     */
    BaseException newException();

    /**
     * 创建异常
     * <br/>
     * @param args 参数列表
     * @return com.jhmarryme.demo.common.base.exception.BaseException
     */
    BaseException newException(Object... args);

    /**
     * 创建异常
     *
     * @param t t
     * @param args 参数列表
     * @return com.jhmarryme.demo.common.base.exception.BaseException
     */
    BaseException newException(Throwable t, Object... args);

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     */
    default void assertNotNull(Object obj) {
        if (obj == null) {
            throw newException();
        }
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     * @param args message占位符对应的参数列表
     */
    default void assertNotNull(Object obj, Object... args) {
        if (obj == null) {
            throw newException(args);
        }
    }

    default void assertTrue(boolean result) {
        if (!result) {
            throw newException();
        }
    }

    default void assertTrue(boolean result, Object... args) {
        if (!result) {
            throw newException(args);
        }
    }
}
