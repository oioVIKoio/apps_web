package com.tecsup.services;

import com.tecsup.dto.ServicioDTO;
import com.tecsup.exception.BadRequestException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Servicio;
import com.tecsup.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository repo;

    public List<Servicio> listar() {
        return repo.findByEstado("A");
    }

    public Servicio obtener(Integer id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Servicio no encontrado con id: " + id));
    }

    public Servicio registrar(ServicioDTO dto) {
        if (repo.existsByNombreAndEmpresa(dto.getNombre(), dto.getEmpresa())) {
            throw new BadRequestException("Ya existe el servicio " + dto.getNombre() + " de " + dto.getEmpresa());
        }
        Servicio servicio = new Servicio();
        copiar(dto, servicio);
        return repo.save(servicio);
    }

    public Servicio actualizar(Integer id, ServicioDTO dto) {
        Servicio servicio = obtener(id);
        if (repo.existsByNombreAndEmpresaAndIdServicioNot(dto.getNombre(), dto.getEmpresa(), id)) {
            throw new BadRequestException("Ya existe el servicio " + dto.getNombre() + " de " + dto.getEmpresa());
        }
        copiar(dto, servicio);
        return repo.save(servicio);
    }

    public void eliminar(Integer id) {
        Servicio servicio = obtener(id);
        servicio.setEstado("I");
        repo.save(servicio);
    }

    private void copiar(ServicioDTO dto, Servicio servicio) {
        servicio.setNombre(dto.getNombre());
        servicio.setEmpresa(dto.getEmpresa());
        servicio.setDescripcion(dto.getDescripcion());
    }
}
