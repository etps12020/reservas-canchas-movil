package com.example.databases.api.edifcios;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestUpdateEdificio {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
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
    public RequestUpdateEdificio() {
    }

    /**
     *
     * @param descripcion
     * @param estado
     * @param direccion
     * @param imagen
     * @param id
     * @param nombre
     */
    public RequestUpdateEdificio(Integer id, String nombre, String direccion, String descripcion, Integer estado, String imagen) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.descripcion = descripcion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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