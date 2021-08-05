package com.jhmarryme.demo.web.service.unitTest.JMockit.capturingexpectations;

import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Animal;
import com.jhmarryme.demo.pojo.model.unitTest.JMockit.Dog;
import mockit.Capturing;
import mockit.Expectations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CapturingExpectationsMock4SubClassTest {
    @Test
    @DisplayName("使用@Capturing, 实例及其子类会被mock")
    void test_capturing_mock_subclass_by_father_class(@Capturing final Animal animal) {
        new Expectations() {
            {
                animal.isHealthy();
                result = true;
            }
        };

        // 子类
        Dog dog = new Dog("", 2);
        Assertions.assertEquals(true, dog.isHealthy());
    }
}
