
package com.example.databases.api.reservas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reserva {

    @SerializedName("fechayHoraCreacion")
    @Expose
    private String fechayHoraCreacion;
    @SerializedName("numReservacion")
    @Expose
    private Integer numReservacion;
    @SerializedName("usuario")
    @Expose
    private Integer usuario;
    @SerializedName("nombreCompleto")
    @Expose
    private String nombreCompleto;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("fechaReservacion")
    @Expose
    private String fechaReservacion;
    @SerializedName("Horario")
    @Expose
    private Integer horario;
    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;
    @SerializedName("horaFin")
    @Expose
    private String horaFin;
    @SerializedName("idCancha")
    @Expose
    private Integer idCancha;
    @SerializedName("cancha")
    @Expose
    private String cancha;
    @SerializedName("idEstado")
    @Expose
    private Integer idEstado;
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
     * @param estado
     * @param horario
     * @param nombreCompleto
     * @param horaInicio
     * @param idCancha
     * @param horaFin
     * @param idEstado
     * @param fechaReservacion
     * @param cancha
     * @param usuario
     * @param fechayHoraCreacion
     * @param telefono
     * @param numReservacion
     */
    public Reserva(String fechayHoraCreacion, Integer numReservacion, Integer usuario, String nombreCompleto, String telefono, String fechaReservacion, Integer horario, String horaInicio, String horaFin, Integer idCancha, String cancha, Integer idEstado, String estado) {
        super();
        this.fechayHoraCreacion = fechayHoraCreacion;
        this.numReservacion = numReservacion;
        this.usuario = usuario;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.fechaReservacion = fechaReservacion;
        this.horario = horario;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idCancha = idCancha;
        this.cancha = cancha;
        this.idEstado = idEstado;
        this.estado = estado;
    }

    public String getFechayHoraCreacion() {
        return fechayHoraCreacion;
    }

    public void setFechayHoraCreacion(String fechayHoraCreacion) {
        this.fechayHoraCreacion = fechayHoraCreacion;
    }

    public Integer getNumReservacion() {
        return numReservacion;
    }

    public void setNumReservacion(Integer numReservacion) {
        this.numReservacion = numReservacion;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(String fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
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

    public Integer getIdCancha() {
        return idCancha;
    }

    public void setIdCancha(Integer idCancha) {
        this.idCancha = idCancha;
    }

    public String getCancha() {
        return cancha;
    }

    public void setCancha(String cancha) {
        this.cancha = cancha;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


}