package com.example.ServiApp.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="consejos")
public class ConsejosModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="tipo_servicio", nullable=false, length=50)
    private String tipoServicio;

    @Column(name="categoria_consumo", nullable=false, length=50)
    private String categoriaConsumo;

    @Column(name="contenido", nullable=false, length=1500)
    private String contenido;

    // Modifica la referencia al administrador
    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private UsuarioModel administrador;

    // relacion de muchos a muchos con PeriodoModel
    @ManyToMany(mappedBy = "consejos")
    private List<PeriodoModel> periodos;

    public ConsejosModel() {
    }

    public ConsejosModel(long id, String tipoServicio, String categoriaConsumo, String contenido,
            UsuarioModel administrador, List<PeriodoModel> periodos) {
        this.id = id;
        this.tipoServicio = tipoServicio;
        this.categoriaConsumo = categoriaConsumo;
        this.contenido = contenido;
        this.administrador = administrador;
        this.periodos = periodos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public UsuarioModel getAdministrador() {
        return administrador;
    }

    public void setAdministrador(UsuarioModel administrador) {
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
        return "ConsejosModel [id=" + id + ", tipoServicio=" + tipoServicio + ", categoriaConsumo=" + categoriaConsumo
                + ", contenido=" + contenido + ", administrador=" + administrador + ", periodos=" + periodos + "]";
    }

    
    
}