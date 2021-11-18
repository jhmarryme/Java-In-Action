package com.example.localmsgtable.service;

import com.example.localmsgtable.db1.dao.AccountMapper;
import com.example.localmsgtable.db1.dao.PaymentMsgMapper;
import com.example.localmsgtable.db1.model.Account;
import com.example.localmsgtable.db1.model.PaymentMsg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 *
 * @author JiaHao Wang
 */
@Service
public class PaymentService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private PaymentMsgMapper paymentMsgMapper;

    /**
     * 模拟简单的支付流程, 类似于支付宝支付
     * @author Jiahao Wang
     * @param userId 用户id
     * @param orderId 订单id
     * @param amount 支付金额
     * @return int
     *      0 => 支付成功
     *      1 => 用户不存在
     *      2 => 余额不足
     */
    @Transactional(transactionManager = "tm1", rollbackFor = Exception.class)
    public int payment(int userId, int orderId, Long amount) {

        // 查询用户
        Account account = accountMapper.selectByPrimaryKey(userId);
        if (account == null) {
            return 1;
        }
        if (account.getAmount().compareTo(amount) < 0) {
            return 2;
        }
        // 扣减金额
        account.setAmount(account.getAmount() - amount);
        // 入库保存
        accountMapper.updateByPrimaryKeySelective(account);
        // 记录消息日志
        PaymentMsg paymentMsg = new PaymentMsg();
        paymentMsg.setOrderId(orderId);
        // 默认未 未发送状态, 发送次数0
        paymentMsg.setStatus(0);
        paymentMsg.setTryCount(0);
        paymentMsgMapper.insert(paymentMsg);
        return 0;
    }
}
