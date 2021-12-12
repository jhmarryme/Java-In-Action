package org.springframework.core;

import com.example.DispatcherServlet;
import com.example.controller.frontend.MainPageController;
import com.example.service.solo.HeadLineService;
import com.example.service.solo.impl.HeadLineServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.core.annotation.Controller;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JiaHao Wang
 * @date 2021/12/12 2:26 下午
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeanContainerTest {

    private static BeanContainer beanContainer;

    @BeforeAll
    static void setUp() {
        beanContainer = BeanContainer.getInstance();
    }

    @Test
    @Order(1)
    @DisplayName("加载目标类及其实例到BeanContainer：loadBeansTest")
    void whenLoadBeansSuccess() {
        assertEquals(false, beanContainer.getLoaded());
        beanContainer.loadBeans("com.example");
        assertEquals(true, beanContainer.getLoaded());
        assertTrue(beanContainer.size() > 1);
    }

    @Test
    @Order(2)
    @DisplayName("根据类获取其实例：getBeanTest")
    void whenGetBeanSuccess() {
        MainPageController controller = (MainPageController) beanContainer.getBean(MainPageController.class);
        assertTrue(controller instanceof MainPageController);
        // DispatcherServlet目前没有自定义注解, 不会被管理起来
        DispatcherServlet servlet = (DispatcherServlet) beanContainer.getBean(DispatcherServlet.class);
        assertNull(servlet);
    }

    @Test
    @Order(3)
    @DisplayName("根据注解获取对应的实例：getClassesByAnnotationTest")
    void getClassesByAnnotationTest() {
        Assertions.assertEquals(true, beanContainer.getLoaded());
        // 有3个类添加了controller注解
        Assertions.assertEquals(3, beanContainer.getClassesByAnnotation(Controller.class).size());
    }

    @Test
    @Order(4)
    @DisplayName("根据接口获取实现类：getClassesBySuperTest")
    void getClassesBySuperTest() {
        Assertions.assertTrue(beanContainer.getLoaded());
        // 通过 HeadLineService.class 获取 HeadLineServiceImpl.class
        assertTrue(beanContainer.getClassesBySuper(HeadLineService.class).contains(HeadLineServiceImpl.class));
    }
}