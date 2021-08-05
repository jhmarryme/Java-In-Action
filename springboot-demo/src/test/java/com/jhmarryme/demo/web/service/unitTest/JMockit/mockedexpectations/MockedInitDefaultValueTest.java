package com.jhmarryme.demo.web.service.unitTest.JMockit.mockedexpectations;

import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Animal;
import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Dog;
import com.jhmarryme.demo.pojo.model.unitTest.JMockit.PetShop;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MockedInitDefaultValueTest {
    @Mocked
    PetShop petShop;
    @Mocked
    Dog dog;

    /** 通过@Mocked注解, 就算petShop和dog没有赋值(由JMockit生成 并设置默认值), 还是可以正常操作 */
    @Test
    void test_mocked_class_init_default_value() {
        // mock对象的方法返回值为基本类型
        Assertions.assertEquals(0.0, dog.getPrice());
        Assertions.assertEquals(0, dog.getAge());
        Assertions.assertEquals(false, petShop.getIsOpen());

        // mock对象的方法返回值为String
        Assertions.assertEquals(null, petShop.getShopName());

        // mock对象的方法返回值为非String的引用类型
        Assertions.assertTrue(petShop.getAnimal("12344") instanceof Animal);
    }
}
