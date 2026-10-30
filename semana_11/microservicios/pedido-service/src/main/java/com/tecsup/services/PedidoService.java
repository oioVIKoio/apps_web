package com.tecsup.services;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.DetallePedido;
import com.tecsup.model.Pedido;
import com.tecsup.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pedido no encontrado con id: " + id));
    }

    public Pedido guardar(Pedido pedido) {
        for (DetallePedido detalle : pedido.getDetalles()) {
            detalle.setPedido(pedido);
        }
        return repository.save(pedido);
    }

    @Transactional
    public Pedido actualizar(Long id, Pedido pedido) {
        Pedido pedidoExistente = buscarPorId(id);
        pedidoExistente.setFecha(pedido.getFecha());
        pedidoExistente.setIdCliente(pedido.getIdCliente());
        pedidoExistente.setTotal(pedido.getTotal());
        pedidoExistente.getDetalles().clear();
        for (DetallePedido detalle : pedido.getDetalles()) {
            detalle.setPedido(pedidoExistente);
            pedidoExistente.getDetalles().add(detalle);
        }
        return repository.save(pedidoExistente);
    }

    public void eliminar(Long id) {
        repository.delete(buscarPorId(id));
    }
}
