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
@Table(name="periodo")

public class PeriodoModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name= "mes", nullable=false, length=50)
    private String mes;

    @Column(name="Consumo", nullable=false, length=50)
    private float consumo;


    //relacion mucho a uno, un perido pertenece a muchos servicios
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private ServicioModel servicio;




    public PeriodoModel() {
    }



    public PeriodoModel(long id, String mes, float consumo, ServicioModel servicio) {
        this.id = id;
        this.mes = mes;
        this.consumo = consumo;
        this.servicio = servicio;
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



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PeriodoModel{");
        sb.append("id=").append(id);
        sb.append(", mes=").append(mes);
        sb.append(", consumo=").append(consumo);
        sb.append(", servicio=").append(servicio);
        sb.append('}');
        return sb.toString();
    }

   
    
    
    

}
