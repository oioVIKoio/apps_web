package com.tecsup.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class EmpleadoDTO {

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Los nombres no pueden pasar de 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden pasar de 100 caracteres")
    private String apellidos;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    @Size(max = 100, message = "El email no puede pasar de 100 caracteres")
    private String email;

    @NotNull(message = "El sueldo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El sueldo debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "El sueldo admite hasta 8 enteros y 2 decimales")
    private BigDecimal sueldo;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }
}
