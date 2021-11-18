package com.example.xademo;

import com.example.xademo.service.XaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XaDemoApplicationTests {

    @Autowired
    private XaService xaService;

	@Test
    @DisplayName("转帐失败-事务回滚失败")
	void whenTransferError() {
        xaService.transfer();
	}

    @Test
    @DisplayName("使用xa的转帐失败-事务回滚成功")
	void whenXaTransferFailed() {
        xaService.xaTransfer();
	}

    @Test
    @DisplayName("转帐失败-通过事务补偿机制")
    public void whenSuccess() {
        // 只做补偿
        xaService.compensationTransfer();

        // 一个使用事务, 一个做补偿
        xaService.compensationTransactionTransfer();
    }
}
