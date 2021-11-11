package com.example.xademo.service;

import com.example.xademo.db1.dao.AccountMapper;
import com.example.xademo.db1.model.Account;
import com.example.xademo.db2.dao.ExternalAccountMapper;
import com.example.xademo.db2.model.ExternalAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * @author JiaHao Wang
 */
@Service
public class XaService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private ExternalAccountMapper externalAccountMapper;

    @Transactional
    public void transfer() {
        Long amount = 200L;

        // 扣200
        Account account = accountMapper.selectByPrimaryKey(1);
        account.setAmount(account.getAmount() - amount);
        accountMapper.updateByPrimaryKeySelective(account);

        // 模拟错误
        int i = 1 / 0;

        // 加200
        ExternalAccount externalAccount = externalAccountMapper.selectByPrimaryKey(1);
        externalAccount.setAmount(externalAccount.getAmount() + amount);
        externalAccountMapper.updateByPrimaryKeySelective(externalAccount);
    }

    // 使用jta的事务管理器
    @Transactional(transactionManager = "xaTransaction")
    public void xaTransfer() {
        Long amount = 200L;

        // 扣200
        Account account = accountMapper.selectByPrimaryKey(1);
        account.setAmount(account.getAmount() - amount);
        accountMapper.updateByPrimaryKeySelective(account);

        // 模拟错误
        int i = 1 / 0;

        // 加200
        ExternalAccount externalAccount = externalAccountMapper.selectByPrimaryKey(1);
        externalAccount.setAmount(externalAccount.getAmount() + amount);
        externalAccountMapper.updateByPrimaryKeySelective(externalAccount);
    }

    @Transactional
    public void compensationTransfer() {
        Long amount = 200L;

        // 扣200
        Account account = accountMapper.selectByPrimaryKey(1);
        account.setAmount(account.getAmount() - amount);
        accountMapper.updateByPrimaryKeySelective(account);

        // 模拟错误
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            // 还原事务
            Account newAccount = accountMapper.selectByPrimaryKey(1);
            newAccount.setAmount(newAccount.getAmount() + amount);
            accountMapper.updateByPrimaryKeySelective(newAccount);
        }

        // 加200
        ExternalAccount externalAccount = externalAccountMapper.selectByPrimaryKey(1);
        externalAccount.setAmount(externalAccount.getAmount() + amount);
        externalAccountMapper.updateByPrimaryKeySelective(externalAccount);
    }

    @Transactional(transactionManager = "tm1")
    public void compensationTransactionTransfer() {
        Long amount = 200L;

        // 扣200
        Account account = accountMapper.selectByPrimaryKey(1);
        account.setAmount(account.getAmount() - amount);
        accountMapper.updateByPrimaryKeySelective(account);

        try {
            // 加200
            ExternalAccount externalAccount = externalAccountMapper.selectByPrimaryKey(1);
            externalAccount.setAmount(externalAccount.getAmount() + amount);
            externalAccountMapper.updateByPrimaryKeySelective(externalAccount);
            int i = 1 / 0;
        } catch (Exception e) {
            // 还原事务
            ExternalAccount newExternalAccount = externalAccountMapper.selectByPrimaryKey(1);
            newExternalAccount.setAmount(newExternalAccount.getAmount() + amount);
            externalAccountMapper.updateByPrimaryKeySelective(newExternalAccount);
            // 让account 通过事务回滚
            throw e;
        }
    }
}
