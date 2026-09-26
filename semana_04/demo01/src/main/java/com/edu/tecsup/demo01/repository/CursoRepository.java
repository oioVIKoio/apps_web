package com.edu.tecsup.demo01.repository;

import com.edu.tecsup.demo01.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}