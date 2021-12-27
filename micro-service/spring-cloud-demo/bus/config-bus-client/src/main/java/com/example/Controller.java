package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/21 下午12:33
 */
@RestController
public class Controller {

    /** 直接注入远程配置中的属性 */
    @Value("${name}")
    private String name;

    /** 注入本地配置中的属性, 本地配置中的属性也是读取远程配置获取的 */
    @Value("${myWords}")
    private String myWords;

    @GetMapping("/name")
    public String name() {
        return this.name;
    }

    @GetMapping("/myWords")
    public String myWords() {
        return this.myWords;
    }
}
