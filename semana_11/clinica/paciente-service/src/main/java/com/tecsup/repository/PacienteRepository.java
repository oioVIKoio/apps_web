package com.tecsup.repository;

import com.tecsup.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    List<Paciente> findByEstado(String estado);

    boolean existsByDni(String dni);

    boolean existsByDniAndIdPacienteNot(String dni, Integer idPaciente);
}
