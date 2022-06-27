package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author JiaHao Wang
 * @date 2022/6/2 下午1:57
 */
class CodeGeneratorTest {

    @Test
    void create() {
        new CodeGenerator().create(CodeGenerator.DataSourceType.TEST);
    }
}