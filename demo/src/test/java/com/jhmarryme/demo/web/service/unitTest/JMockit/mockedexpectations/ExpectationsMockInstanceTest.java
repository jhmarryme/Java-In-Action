package com.jhmarryme.demo.web.service.unitTest.JMockit.mockedexpectations;

import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Dog;
import mockit.Expectations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 1.48移除了传入类名到Expectations构造函数的mock使用方法
 * 下面是将要mock的对象传入Expectations的构造函数
 */
public class ExpectationsMockInstanceTest {
    Dog dogGlobal = new Dog("DaHuang", 2);

    @Test
    void test_mock_a_instance() {
        String expectedStr = "ha ha ha";

        // 将要mock的对象传入Expectations的构造函数，可以mock实例
        Dog dog = new Dog("AHuang", 2);
        // 通过参数传入
        new Expectations(dog) {
            {
                dog.shut();
                result = expectedStr;
            }
        };

        Assertions.assertEquals(expectedStr, dog.shut());
        //只满足mock实例
        Assertions.assertNotEquals(expectedStr, dogGlobal.shut());
    }

}
