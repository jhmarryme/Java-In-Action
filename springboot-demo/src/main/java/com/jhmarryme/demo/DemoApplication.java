package com.jhmarryme.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * demo
 * @author Jiahao Wang
 * @date 2021/3/1 10:49
 */
@MapperScan("com.jhmarryme.demo.mapper")
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
