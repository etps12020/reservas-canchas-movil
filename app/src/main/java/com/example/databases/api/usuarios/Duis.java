package com.example.databases.api.usuarios;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Duis implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("dui")
    @Expose
    private String dui;
    private final static long serialVersionUID = 3688822589216166887L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Duis() {
    }

    /**
     *
     * @param dui
     * @param id
     */
    public Duis(Integer id, String dui) {
        super();
        this.id = id;
        this.dui = dui;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }


}