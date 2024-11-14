package com.example.ServiApp.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="periodos")
public class PeriodoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name= "mes", nullable=false, length=50)
    private String mes;

    @Column(name="Consumo", nullable=false, length=50)
    private float consumo;

    // Relación muchos a uno: un periodo pertenece a un servicio
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioModel servicio;

    // Relación muchos a muchos con ConsejosModel
    @ManyToMany
    @JoinTable(
        name = "periodo_consejo",
        joinColumns = @JoinColumn(name = "periodo_id"),
        inverseJoinColumns = @JoinColumn(name = "consejo_id")
    )
    private List<ConsejosModel> consejos;

    public PeriodoModel() {}

    public PeriodoModel(long id, String mes, float consumo, ServicioModel servicio, List<ConsejosModel> consejos) {
        this.id = id;
        this.mes = mes;
        this.consumo = consumo;
        this.servicio = servicio;
        this.consejos = consejos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public float getConsumo() {
        return consumo;
    }

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }

    public ServicioModel getServicio() {
        return servicio;
    }

    public void setServicio(ServicioModel servicio) {
        this.servicio = servicio;
    }

    public List<ConsejosModel> getConsejos() {
        return consejos;
    }

    public void setConsejos(List<ConsejosModel> consejos) {
        this.consejos = consejos;
    }

    @Override
    public String toString() {
        return "PeriodoModel{" +
                "id=" + id +
                ", mes='" + mes + '\'' +
                ", consumo=" + consumo +
                ", servicio=" + servicio +
                ", consejos=" + consejos +
                '}';
    }
}
