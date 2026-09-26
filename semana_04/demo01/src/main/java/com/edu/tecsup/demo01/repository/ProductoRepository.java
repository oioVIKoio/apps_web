package com.edu.tecsup.demo01.repository;

import com.edu.tecsup.demo01.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaId(Long categoriaId);

}
