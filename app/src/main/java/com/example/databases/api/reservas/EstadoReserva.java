package com.example.databases.api.reservas;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EstadoReserva {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("estado")
    @Expose
    private String estado;

    /**
     * No args constructor for use in serialization
     *
     */
    public EstadoReserva() {
    }

    /**
     *
     * @param estado
     * @param id
     */
    public EstadoReserva(Integer id, String estado) {
        super();
        this.id = id;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getEstado();
    }
}