package com.deven.devcrm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //  This class handles API requests
public class HelloController {
    @GetMapping("/hello")   //  When someone opens /hello, run this method
    public String sayHello() {
        return "Hello Deven, Spring Boot is Working";
    }
}
