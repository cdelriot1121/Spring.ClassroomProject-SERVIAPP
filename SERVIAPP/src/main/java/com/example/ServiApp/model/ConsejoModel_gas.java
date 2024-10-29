package com.example.ServiApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Consejos_agua")

public class ConsejoModel_gas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name="consumos_elevados", nullable = false, length = 1000)
    private String consumo_alto_a;


    @Column(name = "consumos_moderados", nullable = false, length = 1000)
        private String consumo_regular_a;

    @Column(name="columna_bajo", nullable = false, length = 1000 )
    private String consumo_bueno_a;

    public ConsejoModel_gas() {
    }

    public ConsejoModel_gas(long id, String consumo_alto_a, String consumo_regular_a, String consumo_bueno_a) {
        this.id = id;
        this.consumo_alto_a = consumo_alto_a;
        this.consumo_regular_a = consumo_regular_a;
        this.consumo_bueno_a = consumo_bueno_a;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConsumo_alto_a() {
        return consumo_alto_a;
    }

    public void setConsumo_alto_a(String consumo_alto_a) {
        this.consumo_alto_a = consumo_alto_a;
    }

    public String getConsumo_regular_a() {
        return consumo_regular_a;
    }

    public void setConsumo_regular_a(String consumo_regular_a) {
        this.consumo_regular_a = consumo_regular_a;
    }

    public String getConsumo_bueno_a() {
        return consumo_bueno_a;
    }

    public void setConsumo_bueno_a(String consumo_bueno_a) {
        this.consumo_bueno_a = consumo_bueno_a;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConsumoModel_gas{");
        sb.append("id=").append(id);
        sb.append(", consumo_alto_a=").append(consumo_alto_a);
        sb.append(", consumo_regular_a=").append(consumo_regular_a);
        sb.append(", consumo_bueno_a=").append(consumo_bueno_a);
        sb.append('}');
        return sb.toString();
    }


}