package com.example.databases.api.edificios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Edificio {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("idEstado")
    @Expose
    private Integer idEstado;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("imagen")
    @Expose
    private String imagen;

    /**
     * No args constructor for use in serialization
     *
     */
    public Edificio() {
    }

    /**
     *
     * @param descripcion
     * @param idEstado
     * @param estado
     * @param direccion
     * @param imagen
     * @param id
     * @param nombre
     */
    public Edificio(Integer id, String nombre, String direccion, Integer idEstado, String estado, String descripcion, String imagen) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.idEstado = idEstado;
        this.estado = estado;
        this.descripcion = descripcion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}