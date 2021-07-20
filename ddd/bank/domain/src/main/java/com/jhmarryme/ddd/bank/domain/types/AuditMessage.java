package com.jhmarryme.ddd.bank.domain.types;

import com.jhmarryme.ddd.bank.domain.entity.Account;
import com.jhmarryme.ddd.bank.types.AccountNumber;
import com.jhmarryme.ddd.bank.types.Money;
import com.jhmarryme.ddd.bank.types.UserId;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Date;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:54
 */
@Value
public class AuditMessage {

    private UserId userId;
    private AccountNumber source;
    private AccountNumber target;
    private Money money;
    private Date date;

    public AuditMessage(Account sourceAccount, Account targetAccount, Money targetMoney) {
        this.userId = sourceAccount.getUserId();
        this.source = sourceAccount.getAccountNumber();
        this.target = targetAccount.getAccountNumber();
        this.money = targetMoney;
        this.date = new Date();
    }

    public String serialize() {
        return userId + "," + source + "," + target + "," + money + "," + date;
    }

    public static AuditMessage deserialize(String value) {
        // todo
        return null;
    }
}
