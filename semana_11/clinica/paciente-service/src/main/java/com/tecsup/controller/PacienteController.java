package com.tecsup.controller;

import com.tecsup.dto.PacienteDTO;
import com.tecsup.model.Paciente;
import com.tecsup.services.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Paciente> registrar(@Valid @RequestBody PacienteDTO dto) {
        return ResponseEntity.status(201).body(service.registrar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(@PathVariable Integer id,
                                               @Valid @RequestBody PacienteDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok("Paciente eliminado correctamente");
    }
}
