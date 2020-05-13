
package com.example.databases.api.reservas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestReserva {

    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("usuarioAd")
    @Expose
    private Integer usuarioAd;
    @SerializedName("usuario")
    @Expose
    private Integer usuario;
    @SerializedName("dui")
    @Expose
    private String dui;
    @SerializedName("hora")
    @Expose
    private Integer hora;
    @SerializedName("cancha")
    @Expose
    private Integer cancha;
    @SerializedName("tipo")
    @Expose
    private Integer tipo;

    /**
     * No args constructor for use in serialization
     *
     */
    public RequestReserva() {
    }

    /**
     *
     * @param fecha
     * @param tipo
     * @param usuarioAd
     * @param hora
     * @param cancha
     * @param dui
     * @param usuario
     */
    public RequestReserva(String fecha, Integer usuarioAd, Integer usuario, String dui, Integer hora, Integer cancha, Integer tipo) {
        super();
        this.fecha = fecha;
        this.usuarioAd = usuarioAd;
        this.usuario = usuario;
        this.dui = dui;
        this.hora = hora;
        this.cancha = cancha;
        this.tipo = tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getUsuarioAd() {
        return usuarioAd;
    }

    public void setUsuarioAd(Integer usuarioAd) {
        this.usuarioAd = usuarioAd;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public Integer getCancha() {
        return cancha;
    }

    public void setCancha(Integer cancha) {
        this.cancha = cancha;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }


}