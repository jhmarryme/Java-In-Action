package com.jhmarryme.ddd.bank.application.impl;

import com.jhmarryme.ddd.bank.application.TransferService;
import com.jhmarryme.ddd.bank.domain.entity.Account;
import com.jhmarryme.ddd.bank.domain.service.AccountTransferService;
import com.jhmarryme.ddd.bank.domain.types.AuditMessage;
import com.jhmarryme.ddd.bank.external.ExchangeRateService;
import com.jhmarryme.ddd.bank.messaging.AuditMessageProducer;
import com.jhmarryme.ddd.bank.repository.AccountRepository;
import com.jhmarryme.ddd.bank.common.Result;
import com.jhmarryme.ddd.bank.types.*;

import java.math.BigDecimal;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 12:59
 */
public class TransferServiceImplNew implements TransferService {

    private AccountRepository accountRepository;
    private AuditMessageProducer auditMessageProducer;
    private ExchangeRateService exchangeRateService;
    private AccountTransferService accountTransferService;

    @Override
    public Result<Boolean> transfer(Long sourceUserId, String targetAccountNumber, BigDecimal targetAmount,
                                    String targetCurrency) {
        // 参数校验
        Money targetMoney = new Money(targetAmount, new Currency(targetCurrency));

        // 读数据
        Account sourceAccount = accountRepository.find(new UserId(sourceUserId));
        Account targetAccount = accountRepository.find(new AccountNumber(targetAccountNumber));
        ExchangeRate exchangeRate = exchangeRateService.getExchangeRate(sourceAccount.getCurrency(),
                targetMoney.getCurrency());

        // 业务逻辑
        accountTransferService.transfer(sourceAccount, targetAccount, targetMoney, exchangeRate);

        // 保存数据
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        // 发送审计消息
        AuditMessage message = new AuditMessage(sourceAccount, targetAccount, targetMoney);
        auditMessageProducer.send(message);

        return Result.success(true);
    }

}
