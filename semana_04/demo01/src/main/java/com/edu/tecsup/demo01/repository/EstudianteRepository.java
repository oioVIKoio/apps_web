package com.edu.tecsup.demo01.repository;

import com.edu.tecsup.demo01.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}