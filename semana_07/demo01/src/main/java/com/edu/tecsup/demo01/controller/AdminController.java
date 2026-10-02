package com.edu.tecsup.demo01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class AdminController {

    @GetMapping("/dashboard")
    public String panel() {
        return "Bienvenido ADMIN";
    }
}