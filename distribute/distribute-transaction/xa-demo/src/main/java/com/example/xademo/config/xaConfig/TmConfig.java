package com.example.xademo.config.xaConfig;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.UserTransaction;

/**
 *
 * @author JiaHao Wang
 */
@Configuration
public class TmConfig {

    // 只需要使用一个Jta事务管理器
    @Bean("xaTransaction")
    public JtaTransactionManager jtaTransactionManager() {
        UserTransaction userTransaction = new UserTransactionImp();
        UserTransactionManager userTransactionManager = new UserTransactionManager();

        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}
