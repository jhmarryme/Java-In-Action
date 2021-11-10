package com.example.shardingjdbc;

import com.example.shardingjdbc.dao.AreaMapper;
import com.example.shardingjdbc.dao.OrderItemMapper;
import com.example.shardingjdbc.dao.OrderMapper;
import com.example.shardingjdbc.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Test
    @DisplayName("测试绑定表")
    public void whenBindingTablesSuccess() {
        int userId = 1;
        for (int orderId = 1; orderId <= 8; orderId++) {
            Order order = Order.builder()
                    .orderId((long) orderId)
                    .userId(userId)
                    .orderStatus(1)
                    .totalAmount(new BigDecimal("11.11"))
                    .build();
            OrderItem item = OrderItem.builder()
                    .id(orderId)
                    .pruductName("test" + userId)
                    .orderId((long) orderId)
                    .userId(userId)
                    .build();
            int orderInsert = orderMapper.insert(order);
            int orderItemInsert = orderItemMapper.insert(item);
            System.out.printf("res[%d] = %d, %d", orderId, orderInsert, orderItemInsert);
            if (orderId % 2 == 0) {
                userId++;
            }
        }
    }

    @Test
    @DisplayName("测试全局表")
    public void whenInsertGlobalSuccess() {
        for (int i = 0; i < 4; i++) {
            Area area = Area.builder()
                    .id(i)
                    .name("test")
                    .build();
            areaMapper.insert(area);
        }
    }

    @Test
    @DisplayName("测试读写分离")
    public void whenWriteReadSplitSuccess() {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria()
                .andUserIdEqualTo(3);
        for (int i = 0; i < 10; i++) {
            List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
            orderItems.forEach(System.out::println);
        }
    }

    @Test
    @DisplayName("测试绑定表-雪花ID")
    public void whenSnowFlakeBindingTablesSuccess() {
        int userId = 1;
        for (int i = 1; i <= 8; i++) {
            Order order = Order.builder()
                    .userId(userId)
                    .orderStatus(1)
                    .totalAmount(new BigDecimal("11.11"))
                    .build();
            int insertSelective = orderMapper.insertSelective(order);
            System.out.println("insertSelective = " + insertSelective);
            if (i % 2 == 0) {
                userId++;
            }
        }
    }
}