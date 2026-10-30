package com.tecsup.dto;

import jakarta.validation.constraints.*;
public class MedicoDTO {

    @NotBlank(message = "El CMP es obligatorio")
    @Size(max = 20, message = "Máximo 20 caracteres")
    private String cmp;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String apellidos;

    @Size(max = 15, message = "Máximo 15 caracteres")
    private String telefono;

    @Email(message = "El correo no es válido")
    @Size(max = 100, message = "Máximo 100 caracteres")
    private String correo;

    @NotNull(message = "La especialidad es obligatoria")
    private Integer idEspecialidad;

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Integer idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
}
