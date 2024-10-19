package com.example.ServiApp.model;

public class ConsumoModel {
    private int id_servicio;
    private int id_usuario;
    private String mes_consumo;
    private float cant_consumo;
    
    //Constructores
    public ConsumoModel() {
    }

    public ConsumoModel(int id_servicio, int id_usuario, String mes_consumo, float cant_consumo) {
        this.id_servicio = id_servicio;
        this.id_usuario = id_usuario;
        this.mes_consumo = mes_consumo;
        this.cant_consumo = cant_consumo;
    }

    //Getters y Setters
    
    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getMes_consumo() {
        return mes_consumo;
    }

    public void setMes_consumo(String mes_consumo) {
        this.mes_consumo = mes_consumo;
    }

    public float getCant_consumo() {
        return cant_consumo;
    }

    public void setCant_consumo(float cant_consumo) {
        this.cant_consumo = cant_consumo;
    }


}
