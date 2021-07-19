package com.jhmarryme.ddd.bank.domain.entity;

import com.jhmarryme.ddd.bank.types.AccountId;
import com.jhmarryme.ddd.bank.types.AccountNumber;
import com.jhmarryme.ddd.bank.types.Money;
import com.jhmarryme.ddd.bank.types.UserId;
import lombok.Data;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:52
 */
@Data
public class Account {
    private AccountId id;
    private AccountNumber accountNumber;
    private UserId userId;
    private Money available;
    private Money dailyLimit;

    public void withdraw(Money money) {
        // 转出
    }

    public void deposit(Money money) {
        // 转入
    }
}
