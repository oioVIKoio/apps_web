package com.tecsup.laboratorio01.model;


public class InformacionPersonal {

    private String nombre;
    private String apellido;
    private String direccion;
    private String ciudad;
    private String dni;
    private String estadoCivil;
    public InformacionPersonal(
            String nombre,
            String apellido,
            String direccion,
            String ciudad,
            String dni,
            String estadoCivil) {this.nombre = nombre;this.apellido = apellido;this.direccion = direccion;this.ciudad = ciudad;this.dni = dni;
        this.estadoCivil = estadoCivil;
    }

    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public String getDireccion() {
        return direccion;
    }
    public String getCiudad() {
        return ciudad;
    }

    public String getDni() {
        return dni;
    }
    public String getEstadoCivil() {
        return estadoCivil;
    }
}