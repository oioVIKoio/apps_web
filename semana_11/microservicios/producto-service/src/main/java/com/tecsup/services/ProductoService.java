package com.tecsup.services;

import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
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

    public Producto guardar(Producto producto) {
        return repo.save(producto);
    }

    public Producto obtener(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Producto no encontrado con id: " + id
                ));
    }

    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }
}
