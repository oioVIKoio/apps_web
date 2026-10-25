package com.edu.tecsup.demo01.service;

import com.edu.tecsup.demo01.exception.RecursoNoEncontradoException;
import com.edu.tecsup.demo01.model.Categoria;
import com.edu.tecsup.demo01.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> listar() {
        return repo.findAll();
    }

    public Categoria obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + id));
    }

    public Categoria guardar(Categoria c) {
        return repo.save(c);
    }

    public Categoria actualizar(Long id, Categoria datos) {
        Categoria existente = obtener(id);

        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());

        return repo.save(existente);
    }

    public void eliminar(Long id) {
        obtener(id);
        repo.deleteById(id);
    }
}
