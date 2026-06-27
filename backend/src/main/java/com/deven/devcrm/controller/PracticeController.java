package com.deven.devcrm.controller;

import org.springframework.web.bind.annotation.*;
import com.deven.devcrm.dto.MessageRequest;

@RestController
@RequestMapping("/api/practice")
public class PracticeController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Practice Controller";
    }

    @GetMapping("/greet/{name}")
    public String greetUser(@PathVariable String name) {
        return "Hello " + name + ", welcome to Spring Boot!";
    }

    @GetMapping("/sum")
    public int addNumber(@RequestParam int  a, @RequestParam int b) {
        return a + b;
    }

    @PostMapping("/message")
    public String receiveMessage(@RequestBody MessageRequest request) {
        return "Hi " + request.getName() + ", your message is: " + request.getMessage();
    }
}
