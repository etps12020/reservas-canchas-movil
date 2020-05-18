package com.example.databases.api.usuarios;


import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseCancha {

    @SerializedName("cancha")
    @Expose
    private Integer cancha;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("telefonoContacto")
    @Expose
    private String telefonoContacto;
    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;
    @SerializedName("horaFin")
    @Expose
    private String horaFin;
    @SerializedName("idEdificio")
    @Expose
    private Integer idEdificio;
    @SerializedName("edificio")
    @Expose
    private String edificio;
    @SerializedName("idTipoCancha")
    @Expose
    private Integer idTipoCancha;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("idEstado")
    @Expose
    private Integer idEstado;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("imagen")
    @Expose
    private String imagen;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseCancha() {
    }

    /**
     *
     * @param descripcion
     * @param idEdificio
     * @param tipo
     * @param estado
     * @param idTipoCancha
     * @param imagen
     * @param nombre
     * @param horaInicio
     * @param horaFin
     * @param idEstado
     * @param cancha
     * @param telefonoContacto
     * @param edificio
     */
    public ResponseCancha(Integer cancha, String nombre, String descripcion, String telefonoContacto, String horaInicio, String horaFin, Integer idEdificio, String edificio, Integer idTipoCancha, String tipo, Integer idEstado, String estado, String imagen) {
        super();
        this.cancha = cancha;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefonoContacto = telefonoContacto;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idEdificio = idEdificio;
        this.edificio = edificio;
        this.idTipoCancha = idTipoCancha;
        this.tipo = tipo;
        this.idEstado = idEstado;
        this.estado = estado;
        this.imagen = imagen;
    }

    public Integer getCancha() {
        return cancha;
    }

    public void setCancha(Integer cancha) {
        this.cancha = cancha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        this.horaFin = horaFin;
    }

    public Integer getIdEdificio() {
        return idEdificio;
    }

    public void setIdEdificio(Integer idEdificio) {
        this.idEdificio = idEdificio;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public Integer getIdTipoCancha() {
        return idTipoCancha;
    }

    public void setIdTipoCancha(Integer idTipoCancha) {
        this.idTipoCancha = idTipoCancha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getNombre();
    }
}