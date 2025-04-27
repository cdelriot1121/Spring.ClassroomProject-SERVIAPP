package com.example.ServiApp.model;

import org.springframework.data.mongodb.core.mapping.Field;

public class PredioModel {

    @Field(name = "barrio")
    private String barrio;

    @Field(name = "direccion")
    private String direccion;

    @Field(name = "estrato")
    private int estrato;

    /**
     * Enumeración que define los tipos de predio disponibles
     */
    public enum TipoPredio {
        CASA,
        APARTAMENTO
    }

    @Field(name = "tipo_predio")
    private TipoPredio tipoPredio;

    // Constructor vacío
    public PredioModel() {
    }

    // Constructor completo
    public PredioModel(String barrio, String direccion, int estrato, TipoPredio tipoPredio) {
        this.barrio = barrio;
        this.direccion = direccion;
        this.estrato = estrato;
        this.tipoPredio = tipoPredio;
    }

    // Getters y setters
    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public TipoPredio getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(TipoPredio tipoPredio) {
        this.tipoPredio = tipoPredio;
    }

    @Override
    public String toString() {
        return "PredioModel{" +
                "barrio='" + barrio + '\'' +
                ", direccion='" + direccion + '\'' +
                ", estrato=" + estrato +
                ", tipoPredio=" + tipoPredio +
                '}';
    }
}