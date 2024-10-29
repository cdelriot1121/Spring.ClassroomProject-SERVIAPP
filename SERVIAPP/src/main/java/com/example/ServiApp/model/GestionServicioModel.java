package com.example.ServiApp.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="gestion_servicios")
public class GestionServicioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany
    @JoinColumn(name = "servicio_id", nullable = false) 
    private List<ServicioModel> servicio;

    @Column(name= "tipo_servicio",nullable = false, length = 50)
    private String tipo_servicio;

    @Column(name = "mes", nullable = false, length = 50)
    private String mes;

    @Column (name= "consumo",  nullable = false, length = 50)
    private float consumo;

    public GestionServicioModel() {
    }

    public GestionServicioModel(long id, List<ServicioModel> servicio, String tipo_servicio, String mes, float consumo) {
        this.id = id;
        this.servicio = servicio;
        this.tipo_servicio = tipo_servicio;
        this.mes = mes;
        this.consumo = consumo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ServicioModel> getServicio() {
        return servicio;
    }

    public void setServicio(List<ServicioModel> servicio) {
        this.servicio = servicio;
    }

    public String getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipo_servicio = tipo_servicio;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GestionServicioModel{");
        sb.append("id=").append(id);
        sb.append(", servicio=").append(servicio);
        sb.append(", tipo_servicio=").append(tipo_servicio);
        sb.append(", mes=").append(mes);
        sb.append(", consumo=").append(consumo);
        sb.append('}');
        return sb.toString();
    }
}
