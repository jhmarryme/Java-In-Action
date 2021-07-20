package com.jhmarryme.ddd.bank.web;

import com.jhmarryme.ddd.bank.common.Result;
import com.jhmarryme.ddd.bank.application.TransferService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

/**
 *
 * @author JiaHao Wang
 * @date 2021/7/19 13:00
 */
public class TransferController {
    private TransferService transferService;

    public Result<Boolean> transfer(String targetAccountNumber, BigDecimal amount, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return transferService.transfer(userId, targetAccountNumber, amount, "CNY");
    }
}
