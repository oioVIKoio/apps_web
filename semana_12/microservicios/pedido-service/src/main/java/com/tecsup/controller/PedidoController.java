package com.tecsup.controller;

import com.tecsup.dto.EstadoDTO;
import com.tecsup.dto.PedidoDTO;
import com.tecsup.model.Pedido;
import com.tecsup.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PostMapping
    public ResponseEntity<Pedido> registrar(@Valid @RequestBody PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setClienteId(dto.getClienteId());
        pedido.setProductoId(dto.getProductoId());
        pedido.setCantidad(dto.getCantidad());
        return ResponseEntity.status(201).body(service.registrar(pedido));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    // No hay DELETE: un pedido no se elimina, se cambia a ATENDIDO o ANULADO
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id,
                                                @Valid @RequestBody EstadoDTO dto) {
        return ResponseEntity.ok(service.cambiarEstado(id, dto.getEstado()));
    }
}
