package com.tecsup.service;

import com.tecsup.entity.Empleado;
import com.tecsup.exception.RecursoNoEncontradoException;
import com.tecsup.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository repo;

    public List<Empleado> listar() {
        return repo.findAll();
    }

    public Empleado obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empleado no encontrado: " + id));
    }

    public Empleado guardar(Empleado e) {
        return repo.save(e);
    }

    public Empleado actualizar(Long id, Empleado datos) {
        Empleado existente = obtener(id);

        existente.setNombres(datos.getNombres());
        existente.setApellidos(datos.getApellidos());
        existente.setEmail(datos.getEmail());
        existente.setSueldo(datos.getSueldo());

        return repo.save(existente);
    }

    public void eliminar(Long id) {
        obtener(id);
        repo.deleteById(id);
    }
}
