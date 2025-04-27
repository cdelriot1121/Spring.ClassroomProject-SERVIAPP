package com.example.ServiApp.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "cortes")
public class CortesModel {

    @Id
    private String id;

    @Field(name = "tipo_servicio")
    private String tipo_servicio;

    @Field(name = "fecha")
    private Date fecha;

    @Field(name = "motivo")
    private String motivo;

    @Field(name = "barrios")
    private List<String> barrios;

    @Field(name = "hora_inicio")
    private String hora_inicio;

    @Field(name = "hora_final")
    private String hora_final;

    // Referencia al administrador
    @Field(name = "administrador_id")
    private String administradorId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public List<String> getBarrios() {
        return barrios;
    }

    public void setBarrios(List<String> barrios) {
        this.barrios = barrios;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public String getAdministradorId() {
        return administradorId;
    }

    public void setAdministradorId(String administradorId) {
        this.administradorId = administradorId;
    }
}
