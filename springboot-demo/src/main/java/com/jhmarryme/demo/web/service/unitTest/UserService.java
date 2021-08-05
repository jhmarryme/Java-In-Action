package com.jhmarryme.demo.web.service.unitTest;

import com.jhmarryme.demo.mapper.unitTest.UserMapper;
import com.jhmarryme.demo.pojo.model.unitTest.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用于单元测试学习
 * @author JiaHao Wang
 * @date 2021/6/21 16:22
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public Integer insertUser(User user) {
        return userMapper.insert(user);
    }
}
