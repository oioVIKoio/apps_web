package com.tecsup.laboratorio01.controller;

import com.tecsup.laboratorio01.model.InformacionPersonal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class informacionController {

    @GetMapping("/persona")
    public String mostrarInformacion(Model model) {

        InformacionPersonal persona = new InformacionPersonal(
                "Victor Manuel",
                "Santamaria Fabian",
                "Av. Los Olivos 123",
                "Lima",
                "12345678",
                "Soltero"
        );

        model.addAttribute("persona", persona);

        return "informacion-personal";
    }
}