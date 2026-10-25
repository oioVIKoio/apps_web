package com.tecsup.controller;

import com.tecsup.dto.EmpleadoDTO;
import com.tecsup.entity.Empleado;
import com.tecsup.service.EmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    // POST
    @PostMapping
    public ResponseEntity<Empleado> guardar(@Valid @RequestBody EmpleadoDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(aEntidad(dto)));
    }

    // GET
    @GetMapping
    public ResponseEntity<List<Empleado>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody EmpleadoDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, aEntidad(dto)));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }

    private Empleado aEntidad(EmpleadoDTO dto) {
        Empleado e = new Empleado();
        e.setNombres(dto.getNombres());
        e.setApellidos(dto.getApellidos());
        e.setEmail(dto.getEmail());
        e.setSueldo(dto.getSueldo());
        return e;
    }
}
