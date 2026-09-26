package com.edu.tecsup.demo01.service;

import com.edu.tecsup.demo01.models.Curso;
import com.edu.tecsup.demo01.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repo;

    public Curso guardar(Curso curso) {
        return repo.save(curso);
    }

    public List<Curso> listar() {
        return repo.findAll();
    }
}