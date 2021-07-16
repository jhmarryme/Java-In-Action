package com.jhmarryme.demo.web.service.unitTest.JMockit.mockedexpectations;

import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Animal;
import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Dog;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MockedExpectationsRangeInClassTest {
    @Mocked
    Dog dogGlobal;

    /**
     * 使用Mocked Dog 作为测试类属性，作用整个类, 整个类中的Dog都会被mock
     */
    @Test
    public void testMockedExpectationsInGlobal(){
        String mockedInfomation = "mocked information";
        new Expectations(){
            {
                dogGlobal.toString();
                this.result = mockedInfomation;
            }
        };

        Dog dogLocal = new Dog("", 2);
        Assertions.assertEquals(mockedInfomation, dogGlobal.toString());
        Assertions.assertEquals(mockedInfomation, dogLocal.toString());
    }


}
