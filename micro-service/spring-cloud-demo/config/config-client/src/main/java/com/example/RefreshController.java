package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author JiaHao Wang
 * @date 2021/12/21 下午6:15
 */
@RestController
@RequestMapping("refresh")
@RefreshScope
public class RefreshController {

    /** 注入本地配置中的属性, 本地配置中的属性也是读取远程配置获取的 */
    @Value("${myWords}")
    private String myWords;

    @GetMapping("/myWords")
    public String myWords() {
        return this.myWords;
    }
}
