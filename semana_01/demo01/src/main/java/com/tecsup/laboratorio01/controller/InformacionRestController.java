package com.tecsup.laboratorio01.controller;

import com.tecsup.laboratorio01.model.InformacionPersonal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformacionRestController {

    @GetMapping("/api/persona")
    public InformacionPersonal obtenerInformacion() {

        return new InformacionPersonal(
                "Victor Manuel",
                "Santamaria Fabian",
                "Av. Los Olivos 123",
                "Lima",
                "12345678",
                "Soltero"
        );
    }
}