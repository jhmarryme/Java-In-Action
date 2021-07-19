package com.jhmarryme.ddd.bank.repository;

import com.jhmarryme.ddd.bank.domain.entity.Account;
import com.jhmarryme.ddd.bank.types.AccountId;
import com.jhmarryme.ddd.bank.types.AccountNumber;
import com.jhmarryme.ddd.bank.types.UserId;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 13:22
 */
public interface AccountRepository {
    Account find(AccountId id);
    Account find(AccountNumber accountNumber);
    Account find(UserId userId);
    Account save(Account account);
}
