package com.jhmarryme.design.chap12.controller;


import com.jhmarryme.design.chap12.DService.VirtualWalletService;

import java.math.BigDecimal;

/**
 * Controller 层有必要也进行充血领域建模呢？
 * 不需要。Controller 层的 VO是一种 DTO（Data Transfer Object，数据传输对象）。它主要是作为接口的数据传输承载体，将数据发送给其他系统。<p>
 * 从功能上来讲，它理应不包含业务逻辑、只包含数据。所以，我们将它设计成贫血模型也是比较合理的。<p>
 * @author Jiahao Wang
 * @date 2021/8/31 14:23
 */
public class VirtualWalletController {

    // 通过构造函数或者IOC框架注入
    private VirtualWalletService virtualWalletService;

    public BigDecimal getBalance(Long walletId) {
        //查询余额
        return virtualWalletService.getBalance(walletId);
    }

    public void debit(Long walletId, BigDecimal amount) {
        //出账
        virtualWalletService.debit(walletId, amount);
    }

    public void credit(Long walletId, BigDecimal amount) {
        //入账
        virtualWalletService.credit(walletId, amount);
    }

    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        //转账
        virtualWalletService.transfer(fromWalletId, toWalletId, amount);
    }
}