package com.tecsup.controller;

import com.tecsup.dto.ProductoDTO;
import com.tecsup.model.Producto;
import com.tecsup.services.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Producto> guardar(@Valid @RequestBody ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoriaId(dto.getCategoriaId());
        return ResponseEntity.status(201).body(service.guardar(producto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody ProductoDTO dto) {
        Producto existente = service.obtener(id);
        existente.setNombre(dto.getNombre());
        existente.setPrecio(dto.getPrecio());
        existente.setStock(dto.getStock());
        existente.setCategoriaId(dto.getCategoriaId());
        return ResponseEntity.ok(service.guardar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }
}
