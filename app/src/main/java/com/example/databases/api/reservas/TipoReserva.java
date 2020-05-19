package com.example.databases.api.reservas;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class TipoReserva implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    private final static long serialVersionUID = -3346969327577757847L;

    /**
     * No args constructor for use in serialization
     *
     */
    public TipoReserva() {
    }

    /**
     *
     * @param descripcion
     * @param tipo
     * @param id
     */
    public TipoReserva(Integer id, String tipo, String descripcion) {
        super();
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}