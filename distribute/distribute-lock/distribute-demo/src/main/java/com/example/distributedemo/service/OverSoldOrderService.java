package com.example.distributedemo.service;

import com.example.distributedemo.dao.OrderItemMapper;
import com.example.distributedemo.dao.OrderMapper;
import com.example.distributedemo.dao.ProductMapper;
import com.example.distributedemo.model.Order;
import com.example.distributedemo.model.OrderItem;
import com.example.distributedemo.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 超卖现象测试类
 * @author JiaHao Wang
 * @date 2021/10/19 下午5:58
 */
@Service
@Slf4j
public class OverSoldOrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private ProductMapper productMapper;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    /** 购买商品id */
    private int purchaseProductId = 100100;
    /** 购买商品数量 */
    private int purchaseProductNum = 1;

    private Lock lock = new ReentrantLock();

    @Transactional(rollbackFor = Exception.class)
    public Integer overSoldOneCreateOrder() throws Exception {

        // 1. 获取商品库存
        Product product = productMapper.selectByPrimaryKey(purchaseProductId);
        if (product == null) {
            throw new Exception("购买商品：" + purchaseProductId + "不存在");
        }
        // 2. 扣减库存
        Integer currentCount = product.getCount();
        if (purchaseProductNum > currentCount) {
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
        }
        int leftCount = currentCount - purchaseProductNum;
        product.setCount(leftCount);
        product.setUpdateTime(new Date());
        product.setUpdateUser("xxx");
        productMapper.updateByPrimaryKeySelective(product);

        // 3. 更新购买记录
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        // 待处理
        order.setOrderStatus(1);
        order.setReceiverName("xxx");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("xxx");
        order.setUpdateTime(new Date());
        order.setUpdateUser("xxx");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("xxx");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("xxx");
        orderItemMapper.insertSelective(orderItem);

        return order.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer overSoldTwoCreateOrder() throws Exception {

        // 1. 获取商品库存
        Product product = productMapper.selectByPrimaryKey(purchaseProductId);
        if (product == null) {
            throw new Exception("购买商品：" + purchaseProductId + "不存在");
        }
        // 2. 扣减库存
        Integer currentCount = product.getCount();
        if (purchaseProductNum > currentCount) {
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
        }
        // 改为由数据库去更新商品数量， update是有行锁的
        productMapper.updateProductCount(purchaseProductNum, "xxx", new Date(), product.getId());

        // 3. 更新购买记录
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        // 待处理
        order.setOrderStatus(1);
        order.setReceiverName("xxx");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("xxx");
        order.setUpdateTime(new Date());
        order.setUpdateUser("xxx");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("xxx");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("xxx");
        orderItemMapper.insertSelective(orderItem);

        return order.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer checkStockCreateOrder() throws Exception {

        // 1. 获取商品库存
        Product product = productMapper.selectByPrimaryKey(purchaseProductId);
        if (product == null) {
            throw new Exception("购买商品：" + purchaseProductId + "不存在");
        }
        // 2. 扣减库存
        Integer currentCount = product.getCount();
        if (purchaseProductNum > currentCount) {
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
        }
        // 改为由数据库去更新商品数量， update是有行锁的
        productMapper.updateProductCount(purchaseProductNum, "xxx", new Date(), product.getId());
        // 检查商品库存， 如果为负数， 抛出异常
        Product newProduct = productMapper.selectByPrimaryKey(purchaseProductId);
        if (newProduct.getCount() < 0) {
            throw new Exception("商品" + purchaseProductId + "仅剩" + newProduct.getCount() + "件，无法购买");
        }

        // 3. 更新购买记录
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        // 待处理
        order.setOrderStatus(1);
        order.setReceiverName("xxx");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("xxx");
        order.setUpdateTime(new Date());
        order.setUpdateUser("xxx");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("xxx");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("xxx");
        orderItemMapper.insertSelective(orderItem);

        return order.getId();
    }


    @Transactional(rollbackFor = Exception.class)
    public synchronized Integer autoTransactionalSyncMethodCreateOrder() throws Exception {

        // 1. 获取商品库存
        Product product = productMapper.selectByPrimaryKey(purchaseProductId);
        if (product == null) {
            throw new Exception("购买商品：" + purchaseProductId + "不存在");
        }
        // 2. 扣减库存
        Integer currentCount = product.getCount();
        if (purchaseProductNum > currentCount) {
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
        }
        // 改为由数据库去更新商品数量， update是有行锁的
        productMapper.updateProductCount(purchaseProductNum, "xxx", new Date(), product.getId());

        // 3. 更新购买记录
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        // 待处理
        order.setOrderStatus(1);
        order.setReceiverName("xxx");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("xxx");
        order.setUpdateTime(new Date());
        order.setUpdateUser("xxx");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("xxx");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("xxx");
        orderItemMapper.insertSelective(orderItem);

        return order.getId();
    }

    // @Transactional(rollbackFor = Exception.class)
    public synchronized Integer manualTransactionalSyncMethodCreateOrder() throws Exception {
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        // 1. 获取商品库存
        Product product = productMapper.selectByPrimaryKey(purchaseProductId);
        if (product == null) {
            throw new Exception("购买商品：" + purchaseProductId + "不存在");
        }
        // 2. 扣减库存
        Integer currentCount = product.getCount();
        if (purchaseProductNum > currentCount) {
            platformTransactionManager.rollback(transactionStatus);
            throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
        }
        // 改为由数据库去更新商品数量， update是有行锁的
        productMapper.updateProductCount(purchaseProductNum, "xxx", new Date(), product.getId());

        // 3. 更新购买记录
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        // 待处理
        order.setOrderStatus(1);
        order.setReceiverName("xxx");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("xxx");
        order.setUpdateTime(new Date());
        order.setUpdateUser("xxx");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("xxx");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("xxx");
        orderItemMapper.insertSelective(orderItem);

        platformTransactionManager.commit(transactionStatus);
        return order.getId();
    }

    // @Transactional(rollbackFor = Exception.class)
    public synchronized Integer manualTransactionalSyncBlockCreateOrder() throws Exception {
        Product product;
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        synchronized (this) {
            // 1. 获取商品库存
            product = productMapper.selectByPrimaryKey(purchaseProductId);
            if (product == null) {
                throw new Exception("购买商品：" + purchaseProductId + "不存在");
            }
            // 2. 扣减库存
            Integer currentCount = product.getCount();
            if (purchaseProductNum > currentCount) {
                // platformTransactionManager.rollback(transactionStatus);
                throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
            }
            // 改为由数据库去更新商品数量， update是有行锁的
            productMapper.updateProductCount(purchaseProductNum, "xxx", new Date(), product.getId());
            platformTransactionManager.commit(transactionStatus);
        }

        TransactionStatus transactionStatus1 = platformTransactionManager.getTransaction(transactionDefinition);
        // 3. 更新购买记录
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        // 待处理
        order.setOrderStatus(1);
        order.setReceiverName("xxx");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("xxx");
        order.setUpdateTime(new Date());
        order.setUpdateUser("xxx");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("xxx");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("xxx");
        orderItemMapper.insertSelective(orderItem);

        platformTransactionManager.commit(transactionStatus1);
        return order.getId();

    }

    // @Transactional(rollbackFor = Exception.class)
    public synchronized Integer manualTransactionalReentrantLockCreateOrder() throws Exception {
        Product product;

        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
        lock.lock();
        try {
            // 1. 获取商品库存
            product = productMapper.selectByPrimaryKey(purchaseProductId);
            if (product == null) {
                throw new Exception("购买商品：" + purchaseProductId + "不存在");
            }
            // 2. 扣减库存
            Integer currentCount = product.getCount();
            if (purchaseProductNum > currentCount) {
                // platformTransactionManager.rollback(transactionStatus);
                throw new Exception("商品" + purchaseProductId + "仅剩" + currentCount + "件，无法购买");
            }
            // 改为由数据库去更新商品数量， update是有行锁的
            productMapper.updateProductCount(purchaseProductNum, "xxx", new Date(), product.getId());
            platformTransactionManager.commit(transactionStatus);
        } finally {
            lock.unlock();
        }

        TransactionStatus transactionStatus1 = platformTransactionManager.getTransaction(transactionDefinition);
        // 3. 更新购买记录
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        // 待处理
        order.setOrderStatus(1);
        order.setReceiverName("xxx");
        order.setReceiverMobile("13311112222");
        order.setCreateTime(new Date());
        order.setCreateUser("xxx");
        order.setUpdateTime(new Date());
        order.setUpdateUser("xxx");
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("xxx");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateTime(new Date());
        orderItem.setUpdateUser("xxx");
        orderItemMapper.insertSelective(orderItem);

        platformTransactionManager.commit(transactionStatus1);
        return order.getId();

    }
}
