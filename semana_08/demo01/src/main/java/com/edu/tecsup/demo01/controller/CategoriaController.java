package com.edu.tecsup.demo01.controller;

import com.edu.tecsup.demo01.dto.CategoriaDTO;
import com.edu.tecsup.demo01.model.Categoria;
import com.edu.tecsup.demo01.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    // GET
    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    // POST
    @PostMapping
    public ResponseEntity<Categoria> guardar(@Valid @RequestBody CategoriaDTO dto) {
        Categoria c = new Categoria();
        c.setNombre(dto.getNombre());
        c.setDescripcion(dto.getDescripcion());

        return ResponseEntity.status(201).body(service.guardar(c));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id,
                                                @Valid @RequestBody CategoriaDTO dto) {
        Categoria datos = new Categoria();
        datos.setNombre(dto.getNombre());
        datos.setDescripcion(dto.getDescripcion());

        return ResponseEntity.ok(service.actualizar(id, datos));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }
}
