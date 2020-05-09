
package com.example.databases.api.roles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Rol {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rol")
    @Expose
    private String rol;

    /**
     * No args constructor for use in serialization
     *
     */
    public Rol() {
    }

    /**
     *
     * @param id
     * @param rol
     */
    public Rol(Integer id, String rol) {
        super();
        this.id = id;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }


}
