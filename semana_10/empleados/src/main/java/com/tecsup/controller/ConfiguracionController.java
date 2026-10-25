package com.tecsup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/configuracion")
public class ConfiguracionController {

    @Autowired
    private Environment env;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping
    public Map<String, String> obtenerConfiguracion() {
        Map<String, String> config = new LinkedHashMap<>();

        config.put("ambiente", env.getProperty("empresa.ambiente"));
        config.put("perfil", String.join(",", env.getActiveProfiles()));
        config.put("puerto", env.getProperty("local.server.port"));
        // La base de datos se consulta a la conexión real, no al texto de la URL
        config.put("baseDatos", jdbcTemplate.queryForObject("SELECT DATABASE()", String.class));

        return config;
    }
}
