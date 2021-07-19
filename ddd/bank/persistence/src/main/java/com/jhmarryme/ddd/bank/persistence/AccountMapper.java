package com.jhmarryme.ddd.bank.persistence;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 13:14
 */
public interface AccountMapper {
    AccountDO selectById(Long value);

    AccountDO selectByAccountNumber(String value);

    AccountDO selectByUserId(Long id);

    void insert(AccountDO accountDO);

    void update(AccountDO accountDO);
}
