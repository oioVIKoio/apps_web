package com.tecsup.services;

import com.tecsup.client.ClienteClient;
import com.tecsup.client.ProductoClient;
import com.tecsup.dto.ClienteDTO;
import com.tecsup.dto.ProductoDTO;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.exception.ServicioNoDisponibleException;
import com.tecsup.model.Pedido;
import com.tecsup.repository.PedidoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private ProductoClient productoClient;

    public List<Pedido> listar() {
        return repo.findAll();
    }

    public Pedido registrar(Pedido p) {
        // Antes de guardar se confirma que el cliente y el producto existen en sus servicios
        ClienteDTO cliente = obtenerCliente(p.getClienteId());
        ProductoDTO producto = obtenerProducto(p.getProductoId());
        p.setEstado("PENDIENTE");
        Pedido pedido = repo.save(p);
        pedido.setCliente(cliente);
        pedido.setProducto(producto);
        return pedido;
    }

    public Pedido obtener(Long id) {
        Pedido pedido = buscar(id);
        pedido.setCliente(obtenerCliente(pedido.getClienteId()));
        pedido.setProducto(obtenerProducto(pedido.getProductoId()));
        return pedido;
    }

    public Pedido cambiarEstado(Long id, String estado) {
        Pedido pedido = buscar(id);
        pedido.setEstado(estado);
        return repo.save(pedido);
    }

    private Pedido buscar(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Pedido no encontrado con id: " + id
                ));
    }

    private ClienteDTO obtenerCliente(Long clienteId) {
        try {
            return clienteClient.obtenerCliente(clienteId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId);
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("No se pudo comunicar con cliente-service");
        }
    }

    private ProductoDTO obtenerProducto(Long productoId) {
        try {
            return productoClient.obtenerProducto(productoId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + productoId);
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("No se pudo comunicar con producto-service");
        }
    }
}
