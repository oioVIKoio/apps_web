package com.tecsup.repository;

import com.tecsup.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    List<Servicio> findByEstado(String estado);

    boolean existsByNombreAndEmpresaAndEstado(String nombre, String empresa, String estado);

    boolean existsByNombreAndEmpresaAndEstadoAndIdServicioNot(String nombre, String empresa, String estado,
                                                               Integer idServicio);
}
