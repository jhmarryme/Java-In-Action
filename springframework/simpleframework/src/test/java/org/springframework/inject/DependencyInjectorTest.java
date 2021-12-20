package org.springframework.inject;

import com.example.controller.frontend.MainPageController;
import com.example.service.combine.HeadLineShopCategoryCombineService;
import com.example.service.combine.impl.HeadLineShopCategoryCombineServiceImpl;
import com.example.service.combine.impl.HeadLineShopCategoryCombineServiceImpl2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.BeanContainer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JiaHao Wang
 * @date 2021/12/26 1:13 下午
 */

class DependencyInjectorTest {

    @DisplayName("依赖注入test")
    @Test
    void doIocTest() {
        // 1. 加载所有的bean实例
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("com.example");
        // 1.1 是否成功加载
        assertTrue(beanContainer.getLoaded());
        // 1.2 验证容器加载的内容是否正常
        MainPageController mainPageController = (MainPageController) beanContainer.getBean(MainPageController.class);
        assertNotNull(mainPageController);
        // 1.3 未执行依赖注入, mainPageController中的service为null
        assertNull(mainPageController.getHeadLineShopCategoryCombineService());

        // 2. 依赖注入
        new DependencyInjector().doIoc();
        // 2.1 依赖注入后, mainPageController中的service有值
        assertNotNull(mainPageController.getHeadLineShopCategoryCombineService());
        // 2.2 多个实现类的情况, 需要指定注入的类名
        assertTrue(mainPageController.getHeadLineShopCategoryCombineService() instanceof HeadLineShopCategoryCombineServiceImpl);
        assertFalse(mainPageController.getHeadLineShopCategoryCombineService() instanceof HeadLineShopCategoryCombineServiceImpl2);
    }
}