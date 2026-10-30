package com.tecsup.controller;

import com.tecsup.dto.CitaDTO;
import com.tecsup.dto.EstadoCitaDTO;
import com.tecsup.model.Cita;
import com.tecsup.services.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService service;

    @GetMapping
    public ResponseEntity<List<Cita>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Cita> registrar(@Valid @RequestBody CitaDTO dto) {
        return ResponseEntity.status(201).body(service.registrar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Cita> actualizarEstado(@PathVariable Integer id,
                                                 @Valid @RequestBody EstadoCitaDTO dto) {
        return ResponseEntity.ok(service.actualizarEstado(id, dto.getEstado()));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.cancelar(id));
    }
}
