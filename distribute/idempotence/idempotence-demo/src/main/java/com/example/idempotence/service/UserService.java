package com.example.idempotence.service;

import com.example.idempotence.db1.dao.UserMapper;
import com.example.idempotence.db1.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/16 下午2:34
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private CuratorFramework zkClient;

    public List<User> listUsers() {
        return userMapper.selectAllUsers();
    }

    public int delUser(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if (user != null) {
            log.info("用户存在，用户为：" + userId);
            return userMapper.deleteByPrimaryKey(userId);
        }
        log.info("用户不存在存在，用户为：" + userId);
        return 0;
    }

    public User selectById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int insertUser(User user, String token) throws Exception {
        // 通过token 创建分布式锁
        InterProcessMutex lock = new InterProcessMutex(zkClient, "/" + token);
        boolean isLock = lock.acquire(30, TimeUnit.SECONDS);
        if (isLock) {
            return userMapper.insertSelective(user);
        }
        return 0;
    }
}
