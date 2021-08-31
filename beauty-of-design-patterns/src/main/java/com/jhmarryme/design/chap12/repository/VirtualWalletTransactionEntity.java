package com.jhmarryme.design.chap12.repository;

import java.math.BigDecimal;

/**
 * 虚拟钱包转账流水记录
 * @author Jiahao Wang
 * @date 2021/8/31 14:19
 */
public class VirtualWalletTransactionEntity {
    private BigDecimal amount;
    private long createTime;
    private Long fromWalletId;
    private Long toWalletId;
    private Status status;

    public void setAmount(BigDecimal amount) {
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setFromWalletId(Long fromWalletId) {
        this.fromWalletId = fromWalletId;
    }

    public Long getFromWalletId() {
        return fromWalletId;
    }

    public void setToWalletId(Long toWalletId) {
        this.toWalletId = toWalletId;
    }

    public Long getToWalletId() {
        return toWalletId;
    }

    public void setStatus(Status toBeExecuted) {
        this.status = toBeExecuted;
    }

    public Status getStatus() {
        return status;
    }
}
