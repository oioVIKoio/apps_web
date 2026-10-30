package com.tecsup.controller;

import com.tecsup.dto.EspecialidadDTO;
import com.tecsup.model.Especialidad;
import com.tecsup.services.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService service;

    @GetMapping
    public ResponseEntity<List<Especialidad>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Especialidad> registrar(@Valid @RequestBody EspecialidadDTO dto) {
        return ResponseEntity.status(201).body(service.registrar(dto));
    }
}
