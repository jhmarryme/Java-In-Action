package com.example.distributelock.controller;

import com.example.distributelock.dao.DistributeLockMapper;
import com.example.distributelock.model.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 基于数据库悲观锁的分布式锁
 * @author Jiahao Wang
 */
@RestController
@Slf4j
public class DataBaseLockController {

    @Resource
    private DistributeLockMapper distributeLockMapper;

    @RequestMapping("dataBaseDistributeLock")
    @Transactional(rollbackFor = Exception.class)
    public String dataBaseDistributeLock() throws Exception {
        log.info("我进入了方法！");
        // 使用 select...for update， 如果不为null, 代表获取到了数据并获取到了锁， 如果为null, 则可能是没获取到锁， 也可能是数据不存在
        DistributeLock distributeLock = distributeLockMapper.selectDistributeLock("demo");
        if (distributeLock == null) {
            throw new Exception("分布式锁找不到");
        }
        log.info("我进入了锁！");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "我已经执行完成！";
    }
}
