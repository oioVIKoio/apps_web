package com.edu.tecsup.demo01.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${empresa.ambiente}")
    private String ambiente;

    @GetMapping
    public String obtenerAmbiente() {
        return "Ambiente activo: " + ambiente;
    }
}
