package com.edu.tecsup.demo01.controller;

import com.edu.tecsup.demo01.models.Curso;
import com.edu.tecsup.demo01.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @PostMapping
    public Curso guardar(@RequestBody Curso curso) {
        return service.guardar(curso);
    }

    @GetMapping
    public List<Curso> listar() {
        return service.listar();
    }
}