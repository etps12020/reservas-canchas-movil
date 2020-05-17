package com.example.databases.api.horarios;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Horario {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;
    @SerializedName("horaFin")
    @Expose
    private String horaFin;

    /**
     * No args constructor for use in serialization
     */
    public Horario() {
    }

    /**
     * @param horaFin
     * @param id
     * @param horaInicio
     */
    public Horario(Integer id, String horaInicio, String horaFin) {
        super();
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return this.getHoraInicio();
    }
}