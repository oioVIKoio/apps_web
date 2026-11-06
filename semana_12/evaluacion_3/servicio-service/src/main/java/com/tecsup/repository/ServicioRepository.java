package com.tecsup.repository;

import com.tecsup.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    List<Servicio> findByEstado(String estado);

    boolean existsByNombreAndEmpresa(String nombre, String empresa);

    boolean existsByNombreAndEmpresaAndIdServicioNot(String nombre, String empresa, Integer idServicio);
}
