package com.shri.springify.Springify.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {


    @GetMapping("/")
    public String Home()
    {
        return "hello welecome to springify";
    }
}
