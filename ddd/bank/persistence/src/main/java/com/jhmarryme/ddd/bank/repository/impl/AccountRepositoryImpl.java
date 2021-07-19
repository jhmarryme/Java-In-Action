package com.jhmarryme.ddd.bank.repository.impl;

import com.jhmarryme.ddd.bank.domain.entity.Account;
import com.jhmarryme.ddd.bank.persistence.AccountBuilder;
import com.jhmarryme.ddd.bank.persistence.AccountDO;
import com.jhmarryme.ddd.bank.persistence.AccountMapper;
import com.jhmarryme.ddd.bank.repository.AccountRepository;
import com.jhmarryme.ddd.bank.types.AccountId;
import com.jhmarryme.ddd.bank.types.AccountNumber;
import com.jhmarryme.ddd.bank.types.UserId;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 13:15
 */
public class AccountRepositoryImpl implements AccountRepository {

    @Autowired
    private AccountMapper accountDAO;

    @Autowired
    private AccountBuilder accountBuilder;

    @Override
    public Account find(AccountId id) {
        AccountDO accountDO = accountDAO.selectById(id.getValue());
        return accountBuilder.toAccount(accountDO);
    }

    @Override
    public Account find(AccountNumber accountNumber) {
        AccountDO accountDO = accountDAO.selectByAccountNumber(accountNumber.getValue());
        return accountBuilder.toAccount(accountDO);
    }

    @Override
    public Account find(UserId userId) {
        AccountDO accountDO = accountDAO.selectByUserId(userId.getId());
        return accountBuilder.toAccount(accountDO);
    }

    @Override
    public Account save(Account account) {
        AccountDO accountDO = accountBuilder.fromAccount(account);
        if (accountDO.getId() == null) {
            accountDAO.insert(accountDO);
        } else {
            accountDAO.update(accountDO);
        }
        return accountBuilder.toAccount(accountDO);
    }
}
