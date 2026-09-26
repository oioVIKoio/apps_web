package com.edu.tecsup.demo01.service;

import com.edu.tecsup.demo01.models.Curso;
import com.edu.tecsup.demo01.models.Estudiante;
import com.edu.tecsup.demo01.repository.CursoRepository;
import com.edu.tecsup.demo01.repository.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private CursoRepository cursoRepo;

    public List<Estudiante> listar() {
        return estudianteRepo.findAll();
    }

    public Estudiante obtener(Long id) {
        return estudianteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepo.save(estudiante);
    }

    public Estudiante agregarCurso(Long estudianteId, Long cursoId) {

        Estudiante estudiante = obtener(estudianteId);

        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        if (estudiante.getCursos().contains(curso)) {
            throw new RuntimeException("El estudiante ya está inscrito en este curso");
        }

        estudiante.getCursos().add(curso);

        return estudianteRepo.save(estudiante);
    }

    public Estudiante quitarCurso(Long estudianteId, Long cursoId) {

        Estudiante estudiante = obtener(estudianteId);

        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        estudiante.getCursos().remove(curso);

        return estudianteRepo.save(estudiante);
    }

    public Set<Curso> listarCursos(Long estudianteId) {
        return obtener(estudianteId).getCursos();
    }
}