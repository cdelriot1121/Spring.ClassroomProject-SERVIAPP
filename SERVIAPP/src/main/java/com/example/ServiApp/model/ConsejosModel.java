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
    private String tipoServicio;

    @Column(name="categoria_consumo", nullable=false, length=50)
    private String categoriaConsumo;

    @Column(name="contenido", nullable=false, length=1500)
    private String contenido;

    // relacion de muchos a uno, entonces un consejo pertenece a un administrador
    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private AdminModel administrador;

    // relacion de muchos a muchos con PeriodoModel
    @ManyToMany(mappedBy = "consejos")
    private List<PeriodoModel> periodos;

    public ConsejosModel() {
    }

    public ConsejosModel(long id, String tipoServicio, String categoriaConsumo, String contenido,
            AdminModel administrador, List<PeriodoModel> periodos) {
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
        return "ConsejosModel [id=" + id + ", tipoServicio=" + tipoServicio + ", categoriaConsumo=" + categoriaConsumo
                + ", contenido=" + contenido + ", administrador=" + administrador + ", periodos=" + periodos + "]";
    }

    
    
}
