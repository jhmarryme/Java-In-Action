package com.jhmarryme.design.chap12.DService;

import com.jhmarryme.design.chap12.repository.Status;
import com.jhmarryme.design.chap12.repository.VirtualWalletRepository;
import com.jhmarryme.design.chap12.repository.VirtualWalletTransactionEntity;
import com.jhmarryme.design.chap12.repository.VirtualWalletTransactionRepository;

import java.math.BigDecimal;

/**
 * DDD模式下service不负责主要的业务逻辑，那它的指责是什么呢？
 * 1.Service 类负责与 Repository 交流。在我的设计与代码实现中，VirtualWalletService 类负责与 Repository 层打交道，<p>
 * 调用 Respository 类的方法，获取数据库中的数据，转化成领域模型 VirtualWallet，
 * 然后由领域模型 VirtualWallet 来完成业务逻辑，最后调用 Repository 类的方法，将数据存回数据库。
 * (之所以让 VirtualWalletService 类与 Repository 打交道，而不是让领域模型 VirtualWallet 与 Repository 打交道，
 * 那是因为我们想保持领域模型的独立性，不与任何其他层的代码（Repository 层的代码）或开发框架（比如 Spring、MyBatis）耦合在一起，
 * 将流程性的代码逻辑（比如从 DB 中取数据、映射数据）与领域模型的业务逻辑解耦，让领域模型更加可复用)
 * 2.Service 类负责跨领域模型的业务聚合功能。VirtualWalletService 类中的 transfer() 转账函数会涉及两个钱包的操作，<p>
 * 因此这部分业务逻辑无法放到 VirtualWallet 类中，所以，我们暂且把转账业务放到 VirtualWalletService 类中。后期如果转账业务变复杂可以
 * 单独抽出来
 * 3.负责一些非功能性及与三方系统交互的工作。比如幂等、事务、发邮件、发消息、记录日志、调用其他系统的 RPC 接口等，都可以放到 Service 类中。<p>
 */
public class VirtualWalletService {
    // 通过构造函数或者IOC框架注入
    private VirtualWalletRepository walletRepo;
    private VirtualWalletTransactionRepository transactionRepo;

    public VirtualWallet getVirtualWallet(Long walletId) {
        VirtualWallet virtualWallet = walletRepo.getWalletEntity(walletId);
        return virtualWallet;
    }


    public BigDecimal getBalance(Long walletId) {
        return walletRepo.getBalance(walletId);
    }

    public void debit(Long walletId, BigDecimal amount) {
        //service层负责和repository层打交道
        VirtualWallet virtualWallet = walletRepo.getWalletEntity(walletId);
        //虚拟钱包的核心业务流程在Domain层实现
        virtualWallet.debit(amount);
        //service层负责和repository层打交道
        walletRepo.updateBalance(walletId, virtualWallet.getBalance());
    }

    public void credit(Long walletId, BigDecimal amount) {
        VirtualWallet virtualWallet = walletRepo.getWalletEntity(walletId);
        virtualWallet.credit(amount);
        walletRepo.updateBalance(walletId, virtualWallet.getBalance());
    }

    public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
        VirtualWalletTransactionEntity transactionEntity = new VirtualWalletTransactionEntity();
        transactionEntity.setAmount(amount);
        transactionEntity.setCreateTime(System.currentTimeMillis());
        transactionEntity.setFromWalletId(fromWalletId);
        transactionEntity.setToWalletId(toWalletId);
        transactionEntity.setStatus(Status.TO_BE_EXECUTED);
        // 记录交易流水
        Long transactionId = transactionRepo.saveTransaction(transactionEntity);
        try {
            debit(fromWalletId, amount);
            credit(toWalletId, amount);
        } catch (RuntimeException e) {
            transactionRepo.updateStatus(transactionId, Status.CLOSED);
            // ...重新抛出异常
        } catch (Exception e) {
            transactionRepo.updateStatus(transactionId, Status.FAILED);
            // ...重新抛出异常
        }
        // 更新流水记录
        transactionRepo.updateStatus(transactionId, Status.EXECUTED);
    }

}
