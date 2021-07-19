package com.jhmarryme.ddd.bank.application;

import java.math.BigDecimal;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:59
 */
public interface TransferService {
    Result<Boolean> transfer(Long sourceUserId, String targetAccountNumber, BigDecimal targetAmount,
                             String targetCurrency);
}
