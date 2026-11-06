package com.tecsup.repository;

import com.tecsup.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByEstado(String estado);

    boolean existsByDni(String dni);

    boolean existsByDniAndIdClienteNot(String dni, Integer idCliente);
}
