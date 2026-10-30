package com.tecsup.dto;

import jakarta.validation.constraints.*;
public class EstadoCitaDTO {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "PROGRAMADA|CONFIRMADA|ATENDIDA|CANCELADA", message = "Estado inválido: PROGRAMADA, CONFIRMADA, ATENDIDA o CANCELADA")
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
