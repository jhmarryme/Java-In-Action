package com.jhmarryme.demo.web.service.unitTest.JMockit.mockedexpectations;

import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Animal;
import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Dog;
import com.jhmarryme.demo.pojo.model.unitTest.JMockit.PetShop;
import mockit.Expectations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 1.48移除了传入类名到Expectations构造函数的mock使用方法
 * 下面是将要mock的对象传入Expectations的构造函数
 */
public class ExpectationsMockVariousMethodTest {
    Dog dogGlobal = new Dog("DaHuang", 2);

    @Test
    void test_mock_various_method_with_different_parameter() {
        Integer expectedAnimalSize = 10000;
        String expectedStr = "ha ha ha";
        String expectedShopName = "maimai kaixinguo";
        Dog expectedDog = new Dog("AHuang", 2);

        //将要mock的对象传入Expectations的构造函数，可以mock实例
        PetShop petShop = new PetShop("maimain leyuan");
        new Expectations(petShop) {{
            petShop.getAnimal(anyString);
            result = expectedDog;

            petShop.add((Animal) any);
            result = true;

            petShop.getShopName();
            result = expectedShopName;

            PetShop.getMaxAnimalSize();
            this.result = expectedAnimalSize;
        }};

        Assertions.assertEquals(expectedDog, petShop.getAnimal("aHuang"));
        Assertions.assertEquals(true, petShop.add(new Dog("", 2)));
        Assertions.assertEquals(expectedShopName, petShop.getShopName());
        Assertions.assertEquals(expectedAnimalSize, PetShop.getMaxAnimalSize());//只满足mock实例
    }

}
