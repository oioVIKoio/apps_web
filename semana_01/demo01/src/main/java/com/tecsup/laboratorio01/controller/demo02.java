package com.tecsup.laboratorio01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demo02 {

    @GetMapping("/saludo")
    public String info() {
        return "información";
    }
}