package com.jhmarryme.demo.web.service.unitTest.JMockit.mockup;

import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Animal;
import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Dog;
import com.jhmarryme.demo.pojo.model.unitTest.JMockit.PetShop;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * 下将要mock的对象传入MockUp模板中，可以mock整个类的方法
 */
public class MockUpWithInheritClassTest {
    @Test
    void test_mock_various_method_with_different_parameter() {
        Integer expectedAnimalSize = 10000;
        String expectedShopName = "maimai kaixinguo";
        Dog expectedDog = new Dog("AHuang", 2);

        //只给要想mock的方法加上@Mock，不为@Mock的方法不受影响
        new MockUp<PetShop>() {
            @Mock
            public void $init(String shopName) {
            }

            @Mock
            public Integer getMaxAnimalSize() {
                return expectedAnimalSize;
            }

            @Mock
            public Animal getAnimal(String id) {
                return expectedDog;
            }

            @Mock
            public Boolean add(Animal animal) {
                return false;
            }
        };

        PetShop petShop = new PetShop("maimain leyuan");
        Assertions.assertEquals(expectedAnimalSize, PetShop.getMaxAnimalSize());
        Assertions.assertEquals(null, petShop.getShopName());
        Assertions.assertEquals(expectedDog, petShop.getAnimal("aHuang"));
        Assertions.assertEquals(false, petShop.add(new Dog("", 2)));
    }

}
