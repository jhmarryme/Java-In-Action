package com.example.shardingjdbc;

import com.example.shardingjdbc.dao.OrderMapper;
import com.example.shardingjdbc.model.Order;
import com.example.shardingjdbc.model.OrderExample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/4 下午3:02
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class DemoApplicationTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    @DisplayName("测试分库分表")
    public void whenTestSubDatabaseSubTableSuccess() {
        Order order = new Order();
        order.setOrderId(2);
        order.setTotalAmount(new BigDecimal("11.11"));
        order.setOrderStatus(1);
        order.setUserId(2);

        orderMapper.insert(order);
    }
}