package com.example.ServiApp.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "fallas_servicio")
public class Falla_Ser_Model {

    @Id
    private String id;

    @Field(name = "barrio")
    private String barrio;

    @Field(name = "servicio")
    private String servicio;

    @Field(name = "hora")
    private Date hora;

    @Field(name = "comentarios")
    private String comentarios;

    // Referencia al usuario
    @Field(name = "usuario_id")
    private String usuarioId;

    public Falla_Ser_Model() {
    }

    public Falla_Ser_Model(String id, String barrio, String servicio, Date hora, String comentarios, String usuarioId) {
        this.id = id;
        this.barrio = barrio;
        this.servicio = servicio;
        this.hora = hora;
        this.comentarios = comentarios;
        this.usuarioId = usuarioId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }
}
