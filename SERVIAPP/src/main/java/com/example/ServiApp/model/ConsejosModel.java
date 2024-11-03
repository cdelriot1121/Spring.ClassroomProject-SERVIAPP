package com.example.ServiApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Consejos")

public class ConsejosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name="tipo_servicio", nullable=false,length = 50)
    private String tipo_servicio;

    @Column (name="categoria_consumo", nullable=false,length = 50)
    private String categoria_consumo;

     @Column (name="contenido", nullable=false,length = 50)
    private String contenido;
  
 // rwlacion muchos a uno, un consejo pertenece a un administrador
    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private AdminModel administrador;



    public ConsejosModel() {
    }

    public ConsejosModel(AdminModel administrador, String categoria_consumo, String contenido, long id, String tipo_servicio) {
        this.administrador = administrador;
        this.categoria_consumo = categoria_consumo;
        this.contenido = contenido;
        this.id = id;
        this.tipo_servicio = tipo_servicio;
    }



    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }



    public String getTipo_servicio() {
        return tipo_servicio;
    }



    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
    }



    public String getCategoria_consumo() {
        return categoria_consumo;
    }



    public void setCategoria_consumo(String categoria_consumo) {
        this.categoria_consumo = categoria_consumo;
    }



    public String getContenido() {
        return contenido;
    }



    public void setContenido(String contenido) {
        this.contenido = contenido;
    }



    public AdminModel getAdministrador() {
        return administrador;
    }



    public void setAdministrador(AdminModel administrador) {
        this.administrador = administrador;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConsejosModel{");
        sb.append("id=").append(id);
        sb.append(", tipo_servicio=").append(tipo_servicio);
        sb.append(", categoria_consumo=").append(categoria_consumo);
        sb.append(", contenido=").append(contenido);
        sb.append(", administrador=").append(administrador);
        sb.append('}');
        return sb.toString();
    }


    

   
   
}