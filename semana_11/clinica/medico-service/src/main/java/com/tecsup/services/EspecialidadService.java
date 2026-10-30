package com.tecsup.services;

import com.tecsup.dto.EspecialidadDTO;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Especialidad;
import com.tecsup.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository repo;

    public List<Especialidad> listar() {
        return repo.findAll();
    }

    public Especialidad obtener(Integer id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Especialidad no encontrada con id: " + id));
    }

    public Especialidad registrar(EspecialidadDTO dto) {
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(dto.getNombre());
        especialidad.setDescripcion(dto.getDescripcion());
        return repo.save(especialidad);
    }
}
