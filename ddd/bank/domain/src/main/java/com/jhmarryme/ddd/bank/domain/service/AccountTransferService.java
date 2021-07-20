package com.jhmarryme.ddd.bank.domain.service;

import com.jhmarryme.ddd.bank.domain.entity.Account;
import com.jhmarryme.ddd.bank.types.ExchangeRate;
import com.jhmarryme.ddd.bank.types.Money;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:53
 */
public interface AccountTransferService {
    void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate);
}
