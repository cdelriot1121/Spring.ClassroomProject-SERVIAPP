package com.example.ServiApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Consejos_energia")

public class ConsejoModel_energia {


@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name="consumos_elevados", nullable = false, length = 1000)
    private String consumo_alto_e;


    @Column(name = "consumos_moderados", nullable = false, length = 1000)
        private String consumo_regular_e;

    @Column(name="columna_bajo", nullable = false, length = 1000 )
    private String consumo_bueno_e;

    public ConsejoModel_energia(long id, String consumo_alto_e, String consumo_regular_e, String consumo_bueno_e) {
        this.id = id;
        this.consumo_alto_e = consumo_alto_e;
        this.consumo_regular_e = consumo_regular_e;
        this.consumo_bueno_e = consumo_bueno_e;
    }

    public ConsejoModel_energia() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConsumo_alto_e() {
        return consumo_alto_e;
    }

    public void setConsumo_alto_e(String consumo_alto_e) {
        this.consumo_alto_e = consumo_alto_e;
    }

    public String getConsumo_regular_e() {
        return consumo_regular_e;
    }

    public void setConsumo_regular_e(String consumo_regular_e) {
        this.consumo_regular_e = consumo_regular_e;
    }

    public String getConsumo_bueno_e() {
        return consumo_bueno_e;
    }

    public void setConsumo_bueno_e(String consumo_bueno_e) {
        this.consumo_bueno_e = consumo_bueno_e;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConsumoModel_energia{");
        sb.append("id=").append(id);
        sb.append(", consumo_alto_e=").append(consumo_alto_e);
        sb.append(", consumo_regular_e=").append(consumo_regular_e);
        sb.append(", consumo_bueno_e=").append(consumo_bueno_e);
        sb.append('}');
        return sb.toString();
    }

  
    





}
