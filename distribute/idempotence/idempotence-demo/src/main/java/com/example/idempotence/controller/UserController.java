package com.example.idempotence.controller;

import com.example.idempotence.db1.model.User;
import com.example.idempotence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 * @author JiaHao Wang
 * @date 2021/11/16 下午2:34
 */
@Controller
@RequestMapping("user")
public class UserController {

    private Set<String> tokenSet = new HashSet<>();

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/userList", method = {RequestMethod.GET, RequestMethod.POST})
    public String userList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
        List<User> userList = userService.listUsers();
        modelMap.put("users", userList);
        return "user/user-list";
    }

    @RequestMapping("delUser")
    @ResponseBody
    public Map<String, Object> delUser(@RequestParam Integer userId) {
        int result = userService.delUser(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("status", result);
        return map;
    }

    @RequestMapping("userDetail")
    public String userDetail(@RequestParam Integer userId, ModelMap map) {
        User user = userService.selectById(userId);
        map.addAttribute("user", user);
        return "user/user-detail";
    }

    @RequestMapping("updateUser")
    public String updateUser(User user, String token) throws Exception {
        Thread.sleep(1000);
        System.out.println(user);
        if (user.getId() != null) {
            System.out.println("更新用户");
            userService.updateUser(user);
        } else {
            if (tokenSet.contains(token)) {
                System.out.println("添加用户");
                userService.insertUser(user, token);
            } else {
                throw new Exception("token 不存在");
            }
        }
        return "redirect:/user/userList";
    }

    @RequestMapping("register")
    public String register(ModelMap map) {
        String token = UUID.randomUUID().toString();
        // 将token放入前端页面中
        tokenSet.add(token);
        map.addAttribute("user", new User());
        map.addAttribute("token", token);

        return "/user/user-detail";
    }
}
