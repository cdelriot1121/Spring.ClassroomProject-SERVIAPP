package com.example.ServiApp.model;

import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Fallas_Servicio")
public class Falla_Ser_Model {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;






    @Column (name = "barrio", nullable = false, length=60)
    private String barrio;

    @Column (name= "servicio", nullable=false, length=50)
    private String servicio;

    @Column (name="hora", nullable=false, length=50)
    private Time hora;

    @Column (name= "comentarios", nullable = false,length = 50)
    private String comentarios;


 //relacion muchos a uno, una falla puede pertener a muchos usuarios
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    public Falla_Ser_Model() {
    }

    public Falla_Ser_Model(long id, String barrio, String servicio, Time hora, String comentarios,
            UsuarioModel usuario) {
        this.id = id;
        this.barrio = barrio;
        this.servicio = servicio;
        this.hora = hora;
        this.comentarios = comentarios;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }















}
