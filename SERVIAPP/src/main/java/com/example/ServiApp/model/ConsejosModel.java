package com.example.ServiApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name="consejos")
public class ConsejosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="tipo_servicio", nullable=false, length=50)
    private String tipo_servicio;

    @Column(name="categoria_consumo", nullable=false, length=50)
    private String categoria_consumo;

    @Column(name="contenido", nullable=false, length=1500)
    private String contenido;

    // relacion de muchos a uno, entonces un consejo pertenece a un administrador
    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private AdminModel administrador;

    // relacion de muchos a muchos con PeriodoModel
    @ManyToMany(mappedBy = "consejos")
    private List<PeriodoModel> periodos;

    public ConsejosModel() {}

    public ConsejosModel(long id, String tipo_servicio, String categoria_consumo, String contenido, AdminModel administrador) {
        this.id = id;
        this.tipo_servicio = tipo_servicio;
        this.categoria_consumo = categoria_consumo;
        this.contenido = contenido;
        this.administrador = administrador;
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

    public List<PeriodoModel> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<PeriodoModel> periodos) {
        this.periodos = periodos;
    }

    @Override
    public String toString() {
        return "ConsejosModel{" +
                "id=" + id +
                ", tipo_servicio='" + tipo_servicio + '\'' +
                ", categoria_consumo='" + categoria_consumo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", administrador=" + administrador +
                '}';
    }
}
