package com.example.idempotence.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/17 上午11:01
 */
@Configuration
public class ZkConfig {

    @Bean(initMethod="start",destroyMethod = "close")
    public CuratorFramework getCuratorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.newClient("localhost:30061", retryPolicy);
    }
}
