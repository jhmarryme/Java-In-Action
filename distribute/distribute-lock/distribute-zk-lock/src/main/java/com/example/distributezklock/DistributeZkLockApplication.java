package com.example.distributezklock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * zookeeper分布式锁
 *
 * @author Jiahao Wang
 */
@SpringBootApplication
public class DistributeZkLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeZkLockApplication.class, args);
    }

    /**
     * 配置 Curator客户端, 指定初始化及销毁的方法
     *
     * @return org.apache.curator.framework.CuratorFramework
     */
    @Bean(initMethod = "start", destroyMethod = "close")
    public CuratorFramework getCuratorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.newClient("localhost:30061", retryPolicy);
    }
}
