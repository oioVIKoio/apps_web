package com.tecsup.service;

import com.tecsup.model.Detalle;
import com.tecsup.model.Producto;
import com.tecsup.model.Venta;
import com.tecsup.repository.DetalleRepository;
import com.tecsup.repository.ProductoRepository;
import com.tecsup.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleService {

    @Autowired
    private DetalleRepository repo;

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private ProductoRepository productoRepo;

    public List<Detalle> listar() {
        return repo.findAll();
    }

    public Detalle guardar(Detalle detalle) {
        return repo.save(detalle);
    }

    public Detalle obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Detalle actualizar(Long id, Detalle detalle) {

        Detalle existente = obtener(id);

        if (existente == null) {
            return null;
        }

        existente.setCantidad(detalle.getCantidad());
        existente.setPrecio(detalle.getPrecio());
        existente.setSubtotal(detalle.getSubtotal());

        if (detalle.getVenta() != null) {
            Venta venta = ventaRepo
                    .findById(detalle.getVenta().getId())
                    .orElse(null);

            existente.setVenta(venta);
        }

        if (detalle.getProducto() != null) {
            Producto producto = productoRepo
                    .findById(detalle.getProducto().getId())
                    .orElse(null);

            existente.setProducto(producto);
        }

        return repo.save(existente);
    }
}