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
     * <br/>
     * @param args 参数列表
     * @return com.jhmarryme.demo.common.base.exception.BaseException
     */
    BaseException newException(String... args);

    /**
     * 创建异常
     * <br/>
     * @param data 补充数据
     * @param args 参数列表
     * @return com.jhmarryme.demo.common.base.exception.BaseException
     */
    BaseException newException(Object data, String... args);

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
    default void assertNotNull(Object obj, String... args) {
        if (obj == null) {
            throw newException(args);
        }
    }

    /**
     * <p>断言对象<code>obj</code>非空。如果对象<code>obj</code>为空，则抛出异常
     *
     * @param obj 待判断对象
     * @param data 补充数据
     * @param args message占位符对应的参数列表
     */
    default void assertNotNull(Object obj, Object data, String... args) {
        if (obj == null) {
            throw newException(data, args);
        }
    }
}
