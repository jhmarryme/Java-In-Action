package com.jhmarryme.demo.common.base.enums;


import com.jhmarryme.demo.common.base.interfaces.BusinessExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常code
 * @author Jiahao Wang
 * @date 2021/3/3 11:01
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum implements BusinessExceptionAssert {

    /** 成功 */
    SUCCESS("200", "success"),
    /** 处理错误 */
    INTERNAL_SERVER_ERROR("500", ""),
    /** 未知错误 */
    UNKNOWN_ERROR("000", ""),
    /** 失败 */
    BAD_REQUEST("400", ""),
    /** 未登录 */
    NO_AUTHORITY("401", ""),
    /** 测试用 */
    TEST_ERROR("403", ""),
    /** 参数校验错误 */
    PARAM_VERIFICATION_ERROR("405", "{0}不能为空"),
    ;

    /**
     * 返回码
     */
    private final String code;
    /**
     * 返回消息
     */
    private final String message;
}