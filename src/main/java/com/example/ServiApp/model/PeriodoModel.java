package com.example.ServiApp.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "periodos")
public class PeriodoModel {

    @Id
    private String id;

    @Field(name = "mes")
    private String mes;

    @Field(name = "ano")
    private int ano;

    @Field(name = "consumo")
    private float consumo;

    @Field(name = "unidad")
    private String unidad;

    // Referencias al usuario y servicio
    @Field(name = "usuario_id")
    private String usuarioId;
    
    @Field(name = "servicio_id")
    private String servicioId;

    // Lista de consejos embebidos (antes era relación muchos a muchos)
    @Field(name = "consejos")
    private List<ConsejoReferencia> consejosRefs = new ArrayList<>();
    
    // Clase interna para representar la relación con consejos
    public static class ConsejoReferencia {
        private String consejoId;
        
        public ConsejoReferencia() {}
        
        public ConsejoReferencia(String consejoId) {
            this.consejoId = consejoId;
        }

        public String getConsejoId() {
            return consejoId;
        }

        public void setConsejoId(String consejoId) {
            this.consejoId = consejoId;
        }
    }

    public PeriodoModel() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public float getConsumo() {
        return consumo;
    }

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getServicioId() {
        return servicioId;
    }

    public void setServicioId(String servicioId) {
        this.servicioId = servicioId;
    }

    public List<ConsejoReferencia> getConsejosRefs() {
        return consejosRefs;
    }

    public void setConsejosRefs(List<ConsejoReferencia> consejosRefs) {
        this.consejosRefs = consejosRefs;
    }
    
    public void addConsejoRef(String consejoId) {
        if (this.consejosRefs == null) {
            this.consejosRefs = new ArrayList<>();
        }
        this.consejosRefs.add(new ConsejoReferencia(consejoId));
    }

    @Override
    public String toString() {
        return "PeriodoModel{" +
                "id=" + id +
                ", mes='" + mes + '\'' +
                ", ano='" + ano + '\'' +
                ", consumo=" + consumo +
                ", unidad='" + unidad + '\'' +
                ", servicioId='" + servicioId + '\'' +
                ", consejos=" + (consejosRefs != null ? consejosRefs.size() : 0) +
                '}';
    }
}
