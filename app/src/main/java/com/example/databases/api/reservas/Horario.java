package com.example.databases.api.reservas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Horario {

    @SerializedName("Horario")
    @Expose
    private Integer horario;
    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;
    @SerializedName("horaFin")
    @Expose
    private String horaFin;
    @SerializedName("estado")
    @Expose
    private String estado;

    /**
     * No args constructor for use in serialization
     *
     */
    public Horario() {
    }

    /**
     *
     * @param horaFin
     * @param estado
     * @param horario
     * @param horaInicio
     */
    public Horario(Integer horario, String horaInicio, String horaFin, String estado) {
        super();
        this.horario = horario;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
    }

    public Integer getHorario() {
        return horario;
    }

    public void setHorario(Integer horario) {
        this.horario = horario;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}