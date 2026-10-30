package com.tecsup.repository;

import com.tecsup.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicoRepository extends JpaRepository<Medico, Integer> {

    List<Medico> findByEstado(String estado);

    boolean existsByCmp(String cmp);

    boolean existsByCmpAndIdMedicoNot(String cmp, Integer idMedico);
}
