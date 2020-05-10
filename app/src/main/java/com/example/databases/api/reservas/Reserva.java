package com.example.databases.api.reservas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reserva {

    @SerializedName("numReservacion")
    @Expose
    private Integer numReservacion;
    @SerializedName("fechaReservacion")
    @Expose
    private String fechaReservacion;
    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;
    @SerializedName("horaFin")
    @Expose
    private String horaFin;
    @SerializedName("cancha")
    @Expose
    private String cancha;
    @SerializedName("estado")
    @Expose
    private String estado;

    /**
     * No args constructor for use in serialization
     *
     */
    public Reserva() {
    }

    /**
     *
     * @param horaFin
     * @param estado
     * @param fechaReservacion
     * @param cancha
     * @param numReservacion
     * @param horaInicio
     */
    public Reserva(Integer numReservacion, String fechaReservacion, String horaInicio, String horaFin, String cancha, String estado) {
        super();
        this.numReservacion = numReservacion;
        this.fechaReservacion = fechaReservacion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cancha = cancha;
        this.estado = estado;
    }

    public Integer getNumReservacion() {
        return numReservacion;
    }

    public void setNumReservacion(Integer numReservacion) {
        this.numReservacion = numReservacion;
    }

    public String getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(String fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
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

    public String getCancha() {
        return cancha;
    }

    public void setCancha(String cancha) {
        this.cancha = cancha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}