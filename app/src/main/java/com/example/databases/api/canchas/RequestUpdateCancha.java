package com.example.databases.api.canchas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestUpdateCancha {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("horaInicio")
    @Expose
    private String horaInicio;
    @SerializedName("horaFin")
    @Expose
    private String horaFin;
    @SerializedName("idEdificio")
    @Expose
    private Integer idEdificio;
    @SerializedName("idTipoCancha")
    @Expose
    private Integer idTipoCancha;
    @SerializedName("estado")
    @Expose
    private Integer estado;
    @SerializedName("imagen")
    @Expose
    private String imagen;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestUpdateCancha() {
    }

    /**
     *
     * @param descripcion
     * @param horaFin
     * @param idEdificio
     * @param estado
     * @param idTipoCancha
     * @param imagen
     * @param id
     * @param telefono
     * @param nombre
     * @param horaInicio
     */
    public RequestUpdateCancha(Integer id, String nombre, String descripcion, String telefono, String horaInicio, String horaFin, Integer idEdificio, Integer idTipoCancha, Integer estado, String imagen) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.idEdificio = idEdificio;
        this.idTipoCancha = idTipoCancha;
        this.estado = estado;
        this.imagen = imagen;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public Integer getIdTipoCancha() {
        return idTipoCancha;
    }

    public void setIdTipoCancha(Integer idTipoCancha) {
        this.idTipoCancha = idTipoCancha;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


}