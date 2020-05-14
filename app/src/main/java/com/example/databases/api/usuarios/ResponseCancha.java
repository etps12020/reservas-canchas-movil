package com.example.databases.api.usuarios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCancha {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("telefonoContacto")
    @Expose
    private String telefonoContacto;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;
    @SerializedName("horaFin")
    @Expose
    private String horaFin;
    @SerializedName("edificio")
    @Expose
    private String edificio;

    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("fechaCreacion")
    @Expose
    private String fechaCreacion;

    /**
     * No args constructor for use in serialization
     *
     */
    public ResponseCancha() {
    }

    /**
     *
     *
     *
     * @param estado
     *
     *
     *
     * @param fechaCreacion
     * @param descripcion
     * @param id
     * @param horaInicio
     * @param nombre
     *
     * @param fechaCreacion
     * @param horaFin
     * @param tipo
     * @param edificio
     * @param telefonoContacto
     */
    public ResponseCancha(Integer id, String nombre, String horaInicio,String horaFin, String tipo,String descripcion, String edificio,String telefonoContacto,  String estado, String fechaCreacion) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.horaInicio= horaInicio;
        this.descripcion = descripcion;
this.horaFin=horaFin;
this.edificio=edificio;
this.tipo=tipo;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


}
