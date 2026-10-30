package com.tecsup.controller;

import com.tecsup.dto.PedidoDTO;
import com.tecsup.model.DetallePedido;
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
    public List<Pedido> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<Pedido> guardar(@Valid @RequestBody PedidoDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(aEntidad(dto)));
    }

    @PutMapping("/{id}")
    public Pedido actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PedidoDTO dto) {
        return service.actualizar(id, aEntidad(dto));
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "Pedido eliminado correctamente";
    }

    private Pedido aEntidad(PedidoDTO dto) {
        Pedido pedido = new Pedido();
        pedido.setFecha(dto.getFecha());
        pedido.setIdCliente(dto.getIdCliente());
        pedido.setTotal(dto.getTotal());
        pedido.setDetalles(dto.getDetalles().stream().map(d -> {
            DetallePedido detalle = new DetallePedido();
            detalle.setIdProducto(d.getIdProducto());
            detalle.setCantidad(d.getCantidad());
            detalle.setPrecio(d.getPrecio());
            return detalle;
        }).toList());
        return pedido;
    }
}
