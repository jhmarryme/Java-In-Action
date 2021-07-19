package com.jhmarryme.ddd.bank.application;

import lombok.Data;

/**
 * 通用返回结果
 * @author Jiahao Wang
 * @date 2021/3/3 11:01
 */
@Data
public class Result<T> {

    public static Result<Boolean> success(boolean b) {
        return null;
    }
}