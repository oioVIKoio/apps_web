package com.edu.tecsup.demo01.dto;

import jakarta.validation.constraints.*;

public class CategoriaDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede pasar de 50 caracteres")
    private String nombre;

    @Size(max = 200, message = "La descripción no puede pasar de 200 caracteres")
    private String descripcion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
