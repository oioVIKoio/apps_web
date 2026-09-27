package com.edu.tecsup.demo01.service;

import com.edu.tecsup.demo01.model.Producto;
import com.edu.tecsup.demo01.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    public List<Producto> listar() {
        return repo.findAll();
    }
    public List<Producto> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }

    public Producto guardar(Producto p) {
        return repo.save(p);
    }

    public Producto obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    public void eliminar(Long id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException(
                    "Producto con ID " + id + " no existe"
            );
        }

        repo.deleteById(id);
    }
    public Producto actualizar(Long id, Producto producto) {

        Producto existente = repo.findById(id).orElse(null);

        if (existente == null) {
            return null;
        }

        existente.setNombre(producto.getNombre());
        existente.setPrecio(producto.getPrecio());
        existente.setStock(producto.getStock());
        existente.setCategoria(producto.getCategoria());

        return repo.save(existente);
    }
}
