package com.tecsup.controller;

import com.tecsup.dto.ReciboDTO;
import com.tecsup.model.EstadoPago;
import com.tecsup.model.Recibo;
import com.tecsup.services.ReciboService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recibos")
public class ReciboController {

    @Autowired
    private ReciboService service;

    @GetMapping
    public ResponseEntity<List<Recibo>> listar(@RequestParam(required = false) EstadoPago estado) {
        return ResponseEntity.ok(service.listar(estado));
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Recibo>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(service.listarPorCliente(idCliente));
    }

    @PostMapping
    public ResponseEntity<Recibo> registrar(@Valid @RequestBody ReciboDTO dto) {
        return ResponseEntity.status(201).body(service.registrar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recibo> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recibo> actualizar(@PathVariable Integer id,
                                             @Valid @RequestBody ReciboDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PatchMapping("/{id}/pagar")
    public ResponseEntity<Recibo> pagar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.pagar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok("Recibo eliminado correctamente");
    }
}
