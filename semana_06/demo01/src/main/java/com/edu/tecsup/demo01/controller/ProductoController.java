package com.edu.tecsup.demo01.controller;


import java.util.List;

import com.edu.tecsup.demo01.dto.ProductoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edu.tecsup.demo01.model.Producto;
import  com.edu.tecsup.demo01.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    // GET
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/buscar")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return service.buscarPorNombre(nombre);
    }

    // POST
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ProductoDTO dto) {

        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setCategoria(dto.getCategoria());

        Producto guardado = service.guardar(p);

        return ResponseEntity.status(201).body(guardado);
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {

        Producto p = service.obtener(id);

        if (p == null) {
            return ResponseEntity.status(404).body("Producto no encontrado");
        }

        return ResponseEntity.ok(p);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody ProductoDTO dto) {

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setCategoria(dto.getCategoria());

        Producto actualizado = service.actualizar(id, producto);

        if (actualizado == null) {
            return ResponseEntity.status(404).body("Producto no existe");
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        try {
            service.eliminar(id);
            return ResponseEntity.ok("Eliminado correctamente");

        } catch (RuntimeException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
}
