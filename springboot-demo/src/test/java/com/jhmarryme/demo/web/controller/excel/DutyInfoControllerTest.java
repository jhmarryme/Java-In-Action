package com.jhmarryme.demo.web.controller.excel;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author JiaHao Wang
 * @
 * date 2021/3/1 13:52
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class DutyInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    @DisplayName("获取一年的信息")
    void getDutyInfo() {
        String content = mockMvc
                .perform(MockMvcRequestBuilders.get("/duty/get")
                        .param("year", "2021"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        Assertions.assertNotNull(content);
    }
}