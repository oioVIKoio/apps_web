package com.tecsup.controller;
import com.tecsup.model.Producto;
import com.tecsup.model.Venta;
import com.tecsup.model.Detalle;
import com.tecsup.service.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetalleController {

    @Autowired
    private DetalleService service;

    @GetMapping
    public List<Detalle> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<Detalle> guardar(@RequestBody Detalle detalle) {
        return ResponseEntity.status(201).body(service.guardar(detalle));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Detalle> obtener(@PathVariable Long id) {
        Detalle detalle = service.obtener(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Detalle> actualizar(
            @PathVariable Long id,
            @RequestBody Detalle detalle) {

        Detalle actualizado = service.actualizar(id, detalle);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Detalle detalle = service.obtener(id);
        if (detalle == null) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
