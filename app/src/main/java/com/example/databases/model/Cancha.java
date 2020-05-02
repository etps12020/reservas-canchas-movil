package com.example.databases.model;

import com.example.databases.db.ContratoReservas;

public class Cancha
{
    private int idCancha;
    private String nombre;
    private String descripcion;
    private String telefonoContacto;
    private String horaInicio;
    private String horaFin;

    private TipoCancha tipoCancha;
    private Edificio edificio;
    private EstadoCancha estadoCancha;
    private String fechaCreacion;

    public int getIdCancha() {
        return idCancha;
    }

    public void setIdCancha(int idCancha) {
        this.idCancha = idCancha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre= nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
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
        this.horaFin= horaFin;
    }



    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public TipoCancha getTipoCancha() {
        return tipoCancha;
    }

    public void setTipoCancha(TipoCancha tipoCancha) {
        this.tipoCancha = tipoCancha;
    }

    public EstadoCancha getEstadoCancha() {
        return estadoCancha;
    }

    public void setEstadoCancha(EstadoCancha estadoCancha) {
        this.estadoCancha = estadoCancha;
    }
    public Edificio getEdificio() {
        return edificio;
    }

    public void setEdificio(Edificio edificio) {
        this.edificio = edificio;
    }

}
