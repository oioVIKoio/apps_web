package com.tecsup.model;

import jakarta.persistence.*;
import java.time.LocalTime;
@Entity
@Table(name = "horario_medico")
public class HorarioMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idHorario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @Column(length = 15)
    private String diaSemana;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}
