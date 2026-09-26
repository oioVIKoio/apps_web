package com.edu.tecsup.demo01.repository;

import com.edu.tecsup.demo01.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
