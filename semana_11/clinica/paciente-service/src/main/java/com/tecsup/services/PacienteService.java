package com.tecsup.services;

import com.tecsup.dto.PacienteDTO;
import com.tecsup.exception.BadRequestException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Paciente;
import com.tecsup.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repo;

    public List<Paciente> listar() {
        return repo.findByEstado("A");
    }

    public Paciente obtener(Integer id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Paciente no encontrado con id: " + id));
    }

    public Paciente registrar(PacienteDTO dto) {
        if (repo.existsByDni(dto.getDni())) {
            throw new BadRequestException("Ya existe un paciente con el DNI " + dto.getDni());
        }
        Paciente paciente = new Paciente();
        copiar(dto, paciente);
        return repo.save(paciente);
    }

    public Paciente actualizar(Integer id, PacienteDTO dto) {
        Paciente paciente = obtener(id);
        if (repo.existsByDniAndIdPacienteNot(dto.getDni(), id)) {
            throw new BadRequestException("Ya existe un paciente con el DNI " + dto.getDni());
        }
        copiar(dto, paciente);
        return repo.save(paciente);
    }

    public void eliminar(Integer id) {
        Paciente paciente = obtener(id);
        paciente.setEstado("I");
        repo.save(paciente);
    }

    private void copiar(PacienteDTO dto, Paciente paciente) {
        paciente.setDni(dto.getDni());
        paciente.setNombres(dto.getNombres());
        paciente.setApellidos(dto.getApellidos());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setSexo(dto.getSexo());
        paciente.setTelefono(dto.getTelefono());
        paciente.setDireccion(dto.getDireccion());
        paciente.setCorreo(dto.getCorreo());
    }
}
