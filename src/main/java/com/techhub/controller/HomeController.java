package com.techhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class HomeController {
    @GetMapping("/create")
    public String getRegisterPage(){
        return "create";
    }
    @GetMapping
    public String getHomePage(){
        return "home";
    }
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
}
