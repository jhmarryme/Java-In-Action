package com.jhmarryme.ddd.bank.exception;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:48
 */
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
