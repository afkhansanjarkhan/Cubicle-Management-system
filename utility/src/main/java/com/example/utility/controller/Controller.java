package com.example.utility;

import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
