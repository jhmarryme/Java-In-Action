package com.jhmarryme.ddd.bank.exception;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:48
 */
public class InvalidCurrencyException extends RuntimeException {

    public InvalidCurrencyException(String message) {
        super(message);
    }
}
