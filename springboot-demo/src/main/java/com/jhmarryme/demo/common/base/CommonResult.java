package com.jhmarryme.demo.common.base;

import com.jhmarryme.demo.common.base.interfaces.IResponseEnum;
import com.jhmarryme.demo.common.base.enums.ResponseEnum;
import lombok.Data;

/**
 * 通用返回结果
 * @author Jiahao Wang
 * @date 2021/3/3 11:01
 */
@Data
public class CommonResult<T> {

    /** 业务错误码 */
    private Integer code;

    /** 信息描述 */
    private String msg;

    /** 返回参数 */
    private T data;

    private CommonResult(IResponseEnum resultStatus, T data) {
        this.code = resultStatus.getHttpStatus().value();
        this.msg = resultStatus.getCode();
        this.data = data;
    }

    /**
     * 业务成功返回业务代码和描述信息
     */
    public static CommonResult<Void> success() {
        return new CommonResult<>(ResponseEnum.SUCCESS, null);
    }

    /**
     * 业务成功返回业务代码,描述和返回的参数
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(ResponseEnum.SUCCESS, data);
    }

    /**
     * 业务成功返回业务代码,描述和返回的参数
     */
    public static <T> CommonResult<T> success(IResponseEnum resultStatus, T data) {
        if (resultStatus == null) {
            return success(data);
        }
        return new CommonResult<>(resultStatus, data);
    }

    /**
     * 业务异常返回业务代码和描述信息
     */
    public static <T> CommonResult<T> failure() {
        return new CommonResult<>(ResponseEnum.UNKNOWN_ERROR, null);
    }

    /**
     * 业务异常返回业务代码,描述和返回的参数
     */
    public static <T> CommonResult<T> failure(IResponseEnum resultStatus) {
        return failure(resultStatus, null);
    }

    /**
     * 业务异常返回业务代码,描述和返回的参数
     */
    public static <T> CommonResult<T> failure(IResponseEnum resultStatus, T data) {
        if (resultStatus == null) {
            return new CommonResult<>(ResponseEnum.UNKNOWN_ERROR, null);
        }
        return new CommonResult<>(resultStatus, data);
    }
}