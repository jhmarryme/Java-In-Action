package com.example.distributedemo;

import com.example.distributedemo.service.OverSoldOrderService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author JiaHao Wang
 * @date 2021/10/19 下午6:17
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
class DistributeDemoApplicationTest {

    @Autowired
    private OverSoldOrderService overSoldOrderService;

    @SneakyThrows
    @Test
    @DisplayName("超卖现象1：代码中做库存扣减，出现多个订单同时更新库存为0")
    public void whenOverSoldOneTestSuccess() {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    Integer orderId = overSoldOrderService.overSoldOneCreateOrder();
                    System.out.printf("订单id： %d\n", orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    @SneakyThrows
    @Test
    @DisplayName("超卖现象2：出现负数库存")
    public void whenOverSoldTwoTestSuccess() {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    Integer orderId = overSoldOrderService.overSoldTwoCreateOrder();
                    System.out.printf("订单id： %d\n", orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    @SneakyThrows
    @Test
    @DisplayName("超卖现象2：解决方案1（低级的）：更新库存成功后，再次检索商品库存，如果商品为负数，抛出异常")
    public void whenCheckStockTestSuccess() {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    Integer orderId = overSoldOrderService.overSoldTwoCreateOrder();
                    System.out.printf("订单id： %d\n", orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    @SneakyThrows
    @Test
    @DisplayName("超卖现象2-解决方案2 (有误)：使用synchronized方法锁，自动事务提交. 还是会产生两个订单")
    public void whenAutoTransactionalSyncCreateOrderFailed() {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    Integer orderId = overSoldOrderService.autoTransactionalSyncMethodCreateOrder();
                    System.out.printf("订单id： %d\n", orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    @SneakyThrows
    @Test
    @DisplayName("超卖现象2-解决方案3：使用synchronized方法锁，但是手动事务提交")
    public void whenManualTransactionalSyncCreateOrderSuccess() {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    Integer orderId = overSoldOrderService.manualTransactionalSyncMethodCreateOrder();
                    System.out.printf("订单id： %d\n", orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    @SneakyThrows
    @Test
    @DisplayName("超卖现象2-解决方案4：使用synchronized块锁，但是手动事务提交")
    public void whenManualTransactionalSyncBlockCreateOrderSuccess() {

        CountDownLatch countDownLatch = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    Integer orderId = overSoldOrderService.manualTransactionalSyncBlockCreateOrder();
                    System.out.printf("订单id： %d\n", orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    @SneakyThrows
    @Test
    @DisplayName("超卖现象2-解决方案5：使用ReentrantLock锁，但是手动事务提交")
    public void whenManualTransactionalReentrantLockCreateOrderSuccess() {

        CountDownLatch countDownLatch = new CountDownLatch(15);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(15);
        ExecutorService executorService = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 15; i++) {
            executorService.execute(() -> {
                try {
                    cyclicBarrier.await();
                    Integer orderId = overSoldOrderService.manualTransactionalReentrantLockCreateOrder();
                    System.out.printf("订单id： %d\n", orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }
}