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

    public Producto actualizar(Long id, Producto datos) {
        Producto existente = obtener(id);

        if (existente == null) {
            return null;
        }

        existente.setNombre(datos.getNombre());
        existente.setPrecio(datos.getPrecio());
        existente.setStock(datos.getStock());
        existente.setCategoria(datos.getCategoria());

        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

}
