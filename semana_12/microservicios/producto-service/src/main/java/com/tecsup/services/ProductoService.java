package com.tecsup.services;

import com.tecsup.client.CategoriaClient;
import com.tecsup.dto.CategoriaDTO;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.exception.ServicioNoDisponibleException;
import com.tecsup.model.Producto;
import com.tecsup.repository.ProductoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    @Autowired
    private CategoriaClient categoriaClient;

    public List<Producto> listar() {
        return repo.findAll();
    }

    public Producto guardar(Producto p) {
        // La categoría se valida antes de guardar, para no dejar productos con una categoría que no existe
        CategoriaDTO categoria = obtenerCategoria(p.getCategoriaId());
        Producto producto = repo.save(p);
        producto.setCategoria(categoria);
        return producto;
    }

    public Producto obtener(Long id) {
        Producto producto = buscar(id);
        producto.setCategoria(obtenerCategoria(producto.getCategoriaId()));
        return producto;
    }

    public Producto buscar(Long id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Producto no encontrado con id: " + id
                ));
    }

    public void eliminar(Long id) {
        repo.delete(buscar(id));
    }

    private CategoriaDTO obtenerCategoria(Long categoriaId) {
        try {
            return categoriaClient.obtenerCategoria(categoriaId);
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Categoría no encontrada con id: " + categoriaId);
        } catch (FeignException e) {
            throw new ServicioNoDisponibleException("No se pudo comunicar con categoria-service");
        }
    }
}
