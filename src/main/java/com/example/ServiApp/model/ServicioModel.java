package com.example.ServiApp.model;

public class ServicioModel {
    private int id_servicio;
    private String tipo_serv;
    private int poliza;
    private int no_hab_hogar;
    private String linea_atencion;
    private int id_usuario;

    //Constructores ServicioModel
    public ServicioModel() {
    }

    public ServicioModel(int id_servicio, String tipo_serv, int poliza, int no_hab_hogar, String linea_atencion,
            int id_usuario) {
        this.id_servicio = id_servicio;
        this.tipo_serv = tipo_serv;
        this.poliza = poliza;
        this.no_hab_hogar = no_hab_hogar;
        this.linea_atencion = linea_atencion;
        this.id_usuario = id_usuario;
    }

    //Getters and Setters

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getTipo_serv() {
        return tipo_serv;
    }

    public void setTipo_serv(String tipo_serv) {
        this.tipo_serv = tipo_serv;
    }

    public int getPoliza() {
        return poliza;
    }

    public void setPoliza(int poliza) {
        this.poliza = poliza;
    }

    public int getNo_hab_hogar() {
        return no_hab_hogar;
    }

    public void setNo_hab_hogar(int no_hab_hogar) {
        this.no_hab_hogar = no_hab_hogar;
    }

    public String getLinea_atencion() {
        return linea_atencion;
    }

    public void setLinea_atencion(String linea_atencion) {
        this.linea_atencion = linea_atencion;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }


}
