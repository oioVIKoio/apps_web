package com.tecsup.services;

import com.tecsup.client.MedicoClient;
import com.tecsup.client.PacienteClient;
import com.tecsup.dto.CitaDTO;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Cita;
import com.tecsup.repository.CitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitaService {

    @Autowired
    private CitaRepository repo;

    @Autowired
    private PacienteClient pacienteClient;

    @Autowired
    private MedicoClient medicoClient;

    public List<Cita> listar() {
        return repo.findAll();
    }

    public Cita obtener(Integer id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
    }

    public Cita registrar(CitaDTO dto) {
        pacienteClient.validarActivo(dto.getIdPaciente());
        medicoClient.validarActivo(dto.getIdMedico());
        Cita cita = new Cita();
        cita.setFecha(dto.getFecha());
        cita.setHora(dto.getHora());
        cita.setIdPaciente(dto.getIdPaciente());
        cita.setIdMedico(dto.getIdMedico());
        cita.setMotivo(dto.getMotivo());
        return repo.save(cita);
    }

    public Cita actualizarEstado(Integer id, String estado) {
        Cita cita = obtener(id);
        cita.setEstado(estado);
        return repo.save(cita);
    }

    public Cita cancelar(Integer id) {
        return actualizarEstado(id, "CANCELADA");
    }
}
