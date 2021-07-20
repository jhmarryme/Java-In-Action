package com.jhmarryme.ddd.bank.domain.service.impl;

import com.jhmarryme.ddd.bank.domain.entity.Account;
import com.jhmarryme.ddd.bank.domain.service.AccountTransferService;
import com.jhmarryme.ddd.bank.types.ExchangeRate;
import com.jhmarryme.ddd.bank.types.Money;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:53
 */
public class AccountTransferServiceImpl implements AccountTransferService {

    @Override
    public void transfer(Account sourceAccount, Account targetAccount, Money targetMoney, ExchangeRate exchangeRate) {

        Money sourceMoney = exchangeRate.exchangeTo(targetMoney);
        sourceAccount.deposit(sourceMoney);
        targetAccount.withdraw(targetMoney);

    }
}
