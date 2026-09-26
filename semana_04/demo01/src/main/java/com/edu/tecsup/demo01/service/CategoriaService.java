package com.edu.tecsup.demo01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.edu.tecsup.demo01.models.Categoria;
import com.edu.tecsup.demo01.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> listar() {
        return repo.findAll();
    }

    public Categoria guardar(Categoria categoria) {
        return repo.save(categoria);
    }

    public Categoria obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
