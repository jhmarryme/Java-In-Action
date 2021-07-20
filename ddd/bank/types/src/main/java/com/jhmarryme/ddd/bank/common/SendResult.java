package com.jhmarryme.ddd.bank.common;

import lombok.Data;

/**
 * 通用返回结果
 * @author Jiahao Wang
 * @date 2021/3/3 11:01
 */
@Data
public class SendResult<T> {

    public static SendResult<Boolean> success(boolean b) {
        return null;
    }

    public static SendResult success() {
        return null;
    }
}