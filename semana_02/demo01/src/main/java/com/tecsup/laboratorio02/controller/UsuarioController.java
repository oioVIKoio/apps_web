package com.tecsup.laboratorio02.controller;

import com.tecsup.laboratorio02.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/usuarios-view")
    public String verUsuarios(Model model) {

        model.addAttribute("usuarios", usuarioService.listarUsuarios());

        return "usuarios";
    }
}