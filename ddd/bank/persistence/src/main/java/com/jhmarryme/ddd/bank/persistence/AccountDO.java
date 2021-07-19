package com.jhmarryme.ddd.bank.persistence;

import com.jhmarryme.ddd.bank.types.AccountId;
import com.jhmarryme.ddd.bank.types.AccountNumber;
import com.jhmarryme.ddd.bank.types.Money;
import com.jhmarryme.ddd.bank.types.UserId;
import lombok.Data;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 13:14
 */
@Data
public class AccountDO {

    private String id;
    private Long userId;
    private String name;
    private String phone;
}
