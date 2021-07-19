package com.jhmarryme.ddd.bank.persistence;

import com.jhmarryme.ddd.bank.domain.entity.Account;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 13:14
 */
public interface AccountBuilder {
    Account toAccount(AccountDO accountDO);

    AccountDO fromAccount(Account account);
}
