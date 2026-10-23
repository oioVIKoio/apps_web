package com.tecsup.laboratorio02.repository;

import com.tecsup.laboratorio02.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}