package com.jhmarryme.ddd.bank.types;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Money {
    private BigDecimal amount;
    private Currency currency;
    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money money) {
        return null;
    }

    public int compareTo(Money money) {
        return this.amount.compareTo(money.getAmount());
    }

    public Money subtract(Money money) {
        return null;
    }
}