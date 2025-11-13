package com.carproduction.demo.demo.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //ResponseBody + Controller
@RequestMapping("/user")
public class userController {
    
    @GetMapping
    public String notSecureMethodApi(){
        return "This is not secured";
    }

    @GetMapping("/auth")
    public String securedMethodApi(){
        return "This is securedApi";
    }
}
