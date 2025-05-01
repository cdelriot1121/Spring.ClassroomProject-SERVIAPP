package com.example.ServiApp.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;


// La serialización es el proceso de convertir un objeto en una secuencia de bytes,
// lo que permite que dicho objeto sea almacenado (por ejemplo, en Redis) o transmitido (por ejemplo, a través de la red).
// En Java, un objeto debe implementar la interfaz java.io.Serializable para que pueda ser serializado.
// Cuando usamos Redis como almacén de sesiones (como con Spring Session),
// los objetos que se guardan en la sesión —como usuarios autenticados— deben ser serializables.
// Si intentamos guardar un objeto no serializable, se lanzará una excepción como NotSerializableException.
// Al registrar un serializador (como RedisSerializer.java()), le decimos a Spring cómo convertir los objetos en bytes
// para almacenarlos en Redis y luego reconstruirlos cuando sea necesario.
// En este caso, estamos utilizando RedisSerializer.java() para serializar objetos Java de forma predeterminada.

public class PredioModel implements Serializable {
    private static final long serialVersionUID = 1L;

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