package com.edu.tecsup.demo01.repository;

import com.edu.tecsup.demo01.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
