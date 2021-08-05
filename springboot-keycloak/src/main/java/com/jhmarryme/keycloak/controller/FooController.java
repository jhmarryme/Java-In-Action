package com.jhmarryme.keycloak.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/foo")
public class FooController {

    @GetMapping("/bar")
    public String bar() {
        return "jhmarryme";
    }

}
