package com.tecsup.laboratorio02.repository;

import com.tecsup.laboratorio02.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}