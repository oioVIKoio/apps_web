package com.edu.tecsup.demo01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class UserController {

    @GetMapping("/home")
    public String dashboard() {
        return "Bienvenido USER";
    }
}