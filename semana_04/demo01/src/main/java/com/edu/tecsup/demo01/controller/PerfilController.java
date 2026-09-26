package com.edu.tecsup.demo01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.edu.tecsup.demo01.models.Perfil;
import com.edu.tecsup.demo01.service.PerfilService;

@RestController
@RequestMapping("api/perfiles")
public class PerfilController {

    @Autowired
    private PerfilService service;
    
    @GetMapping
    public List<Perfil> listar() {
        return service.listar();
    }

    @PostMapping
    public Perfil guardar(@RequestBody Perfil perfil) {
        return service.guardar(perfil);
    }

    @GetMapping("/{id}")
    public Perfil obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}