package com.tecsup.controller;

import com.tecsup.dto.MedicoDTO;
import com.tecsup.model.Medico;
import com.tecsup.services.MedicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    @Autowired
    private MedicoService service;

    @GetMapping
    public ResponseEntity<List<Medico>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Medico> registrar(@Valid @RequestBody MedicoDTO dto) {
        return ResponseEntity.status(201).body(service.registrar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medico> actualizar(@PathVariable Integer id,
                                               @Valid @RequestBody MedicoDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok("Médico eliminado correctamente");
    }
}
