package com.jhmarryme.design.chap12.repository;

import com.jhmarryme.design.chap12.DService.VirtualWallet;

import java.math.BigDecimal;

/**
 * Repository层, 规范来说应该声明一个接口, 屏蔽数据库底层操作的实现, 作为防腐层<p>
 *     在这里注入dao层, 进行实际的操作<p>
 *     数据库do应该对service层屏蔽, 入参和出参都需要为dto, 使用converter进行转换
 * @author Jiahao Wang
 * @date 2021/8/31 14:31
 */
public class VirtualWalletRepository {

    public VirtualWallet getWalletEntity(Long walletId) {
        return convert(new VirtualWalletEntity());
    }

    public BigDecimal getBalance(Long walletId) {
        return new BigDecimal(100);
    }

    public void updateBalance(Long walletId, BigDecimal subtract) {
    }

    private VirtualWallet convert(VirtualWalletEntity virtualWalletEntity) {
        return new VirtualWallet();
    }
}
