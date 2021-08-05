package com.jhmarryme.demo.web.service.unitTest.JMockit.mockedexpectations;

import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Dog;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MockedExpectationRangeInMethodTest {
    /**
     * 使用Mocked Dog 作为测试方法参数，作用本方法, 类中所有Dog都会被mock
     */
    @Test
    public void testMockedExpectationsInMethodParameter(@Mocked Dog dog) {

        String mockedInfomation = "mocked information";
        Dog dogLocal = new Dog("", 2);

        // 此时toString正常打印
        Assertions.assertNotEquals(mockedInfomation, dogLocal.toString());
        new Expectations() {
            {
                dog.toString();
                this.result = mockedInfomation;
            }
        };

        // 同类型的变量 new出来还是会被mock, toString: mocked information
        Assertions.assertEquals(mockedInfomation, dogLocal.toString());
        Assertions.assertEquals(mockedInfomation, dog.toString());
    }
}
