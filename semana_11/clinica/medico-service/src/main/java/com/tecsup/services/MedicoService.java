package com.tecsup.services;

import com.tecsup.dto.MedicoDTO;
import com.tecsup.exception.BadRequestException;
import com.tecsup.exception.ResourceNotFoundException;
import com.tecsup.model.Medico;
import com.tecsup.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    @Autowired
    private MedicoRepository repo;

    @Autowired
    private EspecialidadService especialidadService;

    public List<Medico> listar() {
        return repo.findByEstado("A");
    }

    public Medico obtener(Integer id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Médico no encontrado con id: " + id));
    }

    public Medico registrar(MedicoDTO dto) {
        if (repo.existsByCmp(dto.getCmp())) {
            throw new BadRequestException("Ya existe un médico con el CMP " + dto.getCmp());
        }
        Medico medico = new Medico();
        copiar(dto, medico);
        return repo.save(medico);
    }

    public Medico actualizar(Integer id, MedicoDTO dto) {
        Medico medico = obtener(id);
        if (repo.existsByCmpAndIdMedicoNot(dto.getCmp(), id)) {
            throw new BadRequestException("Ya existe un médico con el CMP " + dto.getCmp());
        }
        copiar(dto, medico);
        return repo.save(medico);
    }

    public void eliminar(Integer id) {
        Medico medico = obtener(id);
        medico.setEstado("I");
        repo.save(medico);
    }

    private void copiar(MedicoDTO dto, Medico medico) {
        medico.setCmp(dto.getCmp());
        medico.setNombres(dto.getNombres());
        medico.setApellidos(dto.getApellidos());
        medico.setTelefono(dto.getTelefono());
        medico.setCorreo(dto.getCorreo());
        medico.setEspecialidad(especialidadService.obtener(dto.getIdEspecialidad()));
    }
}
