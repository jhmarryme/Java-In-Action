package com.jhmarryme.demo.unitTest;

import com.jhmarryme.demo.pojo.model.unitTest.User;
import com.jhmarryme.demo.web.controller.unitTest.UserController;
import com.jhmarryme.demo.web.service.unitTest.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author JiaHao Wang
 * @date 2021/6/21 16:26
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class JunitTestWithMockito {

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    public void getUserById() throws Exception {
        Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(new User(200, "jhmarryme"));

        User user = userController.getUserById(1);
        //检查结果
        Assertions.assertNotNull(user);
        Assertions.assertEquals(Integer.valueOf(200), user.getId());
        Assertions.assertEquals("jhmarryme", user.getName());
    }
}
