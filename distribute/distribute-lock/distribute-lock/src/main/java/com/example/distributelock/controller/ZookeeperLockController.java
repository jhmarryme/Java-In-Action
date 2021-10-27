package com.example.distributelock.controller;

import com.example.distributelock.lock.ZkLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 */
@RestController
@Slf4j
public class ZookeeperLockController {

    @RequestMapping("zkLock")
    public String zkLock() {
        log.info("我进入了方法！");
        try (ZkLock zkLock = new ZkLock()) {
            if (zkLock.getLock("order")) {
                log.info("我进入了锁！！");
                Thread.sleep(15000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return "方法执行完成";
    }
}
