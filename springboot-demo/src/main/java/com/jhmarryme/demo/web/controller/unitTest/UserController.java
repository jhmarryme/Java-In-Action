package com.jhmarryme.demo.web.controller.unitTest;

import com.jhmarryme.demo.common.annotation.ResponseResultBody;
import com.jhmarryme.demo.pojo.model.unitTest.User;
import com.jhmarryme.demo.web.service.unitTest.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 * @date 2021/6/21 17:55
 */
@RestController
@RequestMapping("unitTest")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    @ResponseResultBody
    public User getUserById(Integer id) {
        return userService.getUserById(id);
    }
}
