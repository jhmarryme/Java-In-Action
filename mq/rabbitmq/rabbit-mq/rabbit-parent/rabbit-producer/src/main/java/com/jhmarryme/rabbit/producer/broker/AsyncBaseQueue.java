package com.jhmarryme.rabbit.producer.broker;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author JiaHao Wang
 * @date 2021/9/26 下午5:28
 */
@Slf4j
public class AsyncBaseQueue {

    /** 线程数量，获取 CPU 核数 */
    private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    /** 队列大小 */
    private static final int QUEUE_SIZE = 10000;

    /** 自定义线程池 */
    private static final ExecutorService executorService = new ThreadPoolExecutor(
            THREAD_SIZE,
            THREAD_SIZE,
            60L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(QUEUE_SIZE),
            // 线程池工厂, 设置线程的名称
            new ThreadFactoryBuilder().setNameFormat("rabbitmq_client_async_sender-%d").build(),
            // 拒绝策略
            (runnable, threadPoolExecutor) -> log.error("async sender is error rejected, runnable: {}, executor: {}",
                    runnable, threadPoolExecutor)

    );

    private AsyncBaseQueue() {
    }


    /**
     * 提交任务到线程池中
     *
     * @param runnable runnable
     */
    public static void submit(Runnable runnable) {
        executorService.submit(runnable);
    }
}
