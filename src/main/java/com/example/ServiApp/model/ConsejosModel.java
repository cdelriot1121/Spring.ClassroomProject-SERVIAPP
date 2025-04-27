package com.example.ServiApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "consejos")
public class ConsejosModel {

    @Id
    private String id;

    @Field(name = "tipo_servicio")
    private String tipoServicio;

    @Field(name = "categoria_consumo")
    private String categoriaConsumo;

    @Field(name = "contenido")
    private String contenido;

    // Referencia al administrador
    @Field(name = "administrador_id")
    private String administradorId;

    public ConsejosModel() {
    }

    public ConsejosModel(String id, String tipoServicio, String categoriaConsumo, String contenido, String administradorId) {
        this.id = id;
        this.tipoServicio = tipoServicio;
        this.categoriaConsumo = categoriaConsumo;
        this.contenido = contenido;
        this.administradorId = administradorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getCategoriaConsumo() {
        return categoriaConsumo;
    }

    public void setCategoriaConsumo(String categoriaConsumo) {
        this.categoriaConsumo = categoriaConsumo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getAdministradorId() {
        return administradorId;
    }

    public void setAdministradorId(String administradorId) {
        this.administradorId = administradorId;
    }

    @Override
    public String toString() {
        return "ConsejosModel{" +
                "id=" + id +
                ", tipoServicio='" + tipoServicio + '\'' +
                ", categoriaConsumo='" + categoriaConsumo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", administradorId='" + administradorId + '\'' +
                '}';
    }
}