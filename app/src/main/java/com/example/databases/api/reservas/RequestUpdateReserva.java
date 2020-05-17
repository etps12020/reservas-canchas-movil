package com.example.databases.api.reservas;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestUpdateReserva implements Serializable
{

    @SerializedName("numReserva")
    @Expose
    private Integer numReserva;
    @SerializedName("usuario")
    @Expose
    private Integer usuario;
    @SerializedName("estado")
    @Expose
    private Integer estado;
    @SerializedName("comentario")
    @Expose
    private String comentario;
    private final static long serialVersionUID = -3105194144436520228L;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestUpdateReserva() {
    }

    /**
     *
     * @param numReserva
     * @param estado
     * @param usuario
     * @param comentario
     */
    public RequestUpdateReserva(Integer numReserva, Integer usuario, Integer estado, String comentario) {
        super();
        this.numReserva = numReserva;
        this.usuario = usuario;
        this.estado = estado;
        this.comentario = comentario;
    }

    public Integer getNumReserva() {
        return numReserva;
    }

    public void setNumReserva(Integer numReserva) {
        this.numReserva = numReserva;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }


}