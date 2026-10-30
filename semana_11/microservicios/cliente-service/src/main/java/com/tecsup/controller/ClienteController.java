package com.tecsup.controller;

import com.tecsup.dto.ClienteDTO;
import com.tecsup.model.Cliente;
import com.tecsup.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Cliente> guardar(@Valid @RequestBody ClienteDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellidos(dto.getApellidos());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setNroDni(dto.getNroDni());
        cliente.setEstado(dto.getEstado());
        return ResponseEntity.status(201).body(service.guardar(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody ClienteDTO dto) {
        Cliente existente = service.obtener(id);
        existente.setNombre(dto.getNombre());
        existente.setApellidos(dto.getApellidos());
        existente.setDireccion(dto.getDireccion());
        existente.setTelefono(dto.getTelefono());
        existente.setNroDni(dto.getNroDni());
        existente.setEstado(dto.getEstado());
        return ResponseEntity.ok(service.guardar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado correctamente");
    }
}
