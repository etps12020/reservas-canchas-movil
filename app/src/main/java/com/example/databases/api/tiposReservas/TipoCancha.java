package com.example.databases.api.tiposReservas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TipoCancha {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tipo")
    @Expose
    private String tipo;


    /**
     * No args constructor for use in serialization
     *
     */
    public TipoCancha() {
    }

    /**
     *

     * @param tipo
     * @param id
     */
    public TipoCancha(Integer id, String tipo) {
        super();
        this.id = id;
        this.tipo = tipo;

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


}
