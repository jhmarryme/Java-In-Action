package org.springframework.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author JiaHao Wang
 * @date 2021/12/8 10:59 下午
 */
class ClassUtilTest {

    @DisplayName("提取目标包下的所有class文件")
    @Test
    void extractPackageClass() {
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.example.entity");
        assertEquals(4, classSet.size());
    }
}