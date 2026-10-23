package com.tecsup.laboratorio01.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;



@RestController
public class demo01 {

    @GetMapping("/informacion")
    public Map<String, Object> info() {

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Nombres", "Victor Santamaria");

        return respuesta;
    }
}