package com.jhmarryme.ddd.bank.domain.entity;

import com.jhmarryme.ddd.bank.exception.DailyLimitExceededException;
import com.jhmarryme.ddd.bank.exception.InsufficientFundsException;
import com.jhmarryme.ddd.bank.exception.InvalidCurrencyException;
import com.jhmarryme.ddd.bank.types.*;
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
        if (this.available.compareTo(money) < 0) {
            throw new InsufficientFundsException();
        }
        if (this.dailyLimit.compareTo(money) < 0) {
            throw new DailyLimitExceededException();
        }
        this.available = this.available.subtract(money);
    }

    public void deposit(Money money) {
        // 转入
        if (!this.getCurrency().equals(money.getCurrency())) {
            throw new InvalidCurrencyException();
        }
        this.available = this.available.add(money);
    }

    public Currency getCurrency() {
        return this.available.getCurrency();
    }
}
