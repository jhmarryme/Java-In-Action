package com.example.localmsgtable.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/15 下午1:18
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Test
    void handleOrder() {
        orderService.handleOrder(1);
    }
}