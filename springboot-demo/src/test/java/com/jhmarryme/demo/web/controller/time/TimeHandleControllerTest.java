package com.jhmarryme.demo.web.controller.time;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author JiaHao Wang
 * @date 2021/6/21 10:53
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TimeHandleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    @DisplayName("后端存储时间")
    void timeHandle() {
        String content = "{\n" +
                "  \" \": \"2020-04-16 16:45:44\",\n" +
                "  \"createAtInDate\": \"2020-04-17 16:45:44\",\n" +
                "  \"createAtInString\": \"2020-04-18 16:45:44\",\n" +
                "  \"updatedAt\":\"2030-04-16T16:45:44Z\"\n" +
                "}";

        String result =
                mockMvc.perform(MockMvcRequestBuilders.post("/time/handle").content(content).contentType(MediaType.APPLICATION_JSON_UTF8)
                        .param("time", "123"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("result = " + result);
    }

    @Test
    @DisplayName("后端接收前端各种格式的时间")
    @SneakyThrows
    public void whenReceiveTimeSuccess() {
        //language=JSON
        String content =
                "{\n" +
                        "  \"localDateTime\": \"2020-04-16 16:45:44\",\n" +
                        "  \"localDate\": \"2020-04-16\",\n" +
                        "  \"localTime\": \"16:45:44\",\n" +
                        "  \"date\": \"2020-04-16 16:45:44\",\n" +
                        "  \"dateString\": \"2020-04-16 16:45:44\",\n" +
                        "  \"instant\": \"2020-04-16T16:45:44Z\"\n" +
                        "}";
        String result = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/time/receive")
                        .content(content).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("date", "2020-04-17 16:45:44"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("result = " + result);
    }
}