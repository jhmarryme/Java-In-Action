package com.jhmarryme.ddd.bank.common;

import lombok.Data;

/**
 * 通用返回结果 临时放在该包下, 具体具体待研究
 * @author Jiahao Wang
 * @date 2021/3/3 11:01
 */
@Data
public class Result<T> {

    public static Result<Boolean> success(boolean b) {
        return null;
    }
}