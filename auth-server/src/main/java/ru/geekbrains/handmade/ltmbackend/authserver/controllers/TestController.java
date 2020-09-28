package ru.geekbrains.handmade.ltmbackend.authserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @RequestMapping("/test")
    public String hello() {
        return "Hello World";
    }
}

