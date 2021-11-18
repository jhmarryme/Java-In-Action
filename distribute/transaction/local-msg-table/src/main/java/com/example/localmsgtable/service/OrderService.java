package com.example.localmsgtable.service;

import com.example.localmsgtable.db2.dao.OrderMapper;
import com.example.localmsgtable.db2.model.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author JiaHao Wang
 */
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    /**
     * 订单回调接口, 类似于 使用支付宝成功支付后, 支付宝会调用此接口通知我们订单已经付款成功了
     *
     * @author Jiahao Wang
     * @param orderId 订单id
     * @return int
     *      0 => 处理成功
     *      1 => 订单不存在
     */
    public int handleOrder(int orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            return 1;
        }
        // 标记为已支付
        order.setStatus(1);
        orderMapper.updateByPrimaryKey(order);
        return 0;
    }
}
