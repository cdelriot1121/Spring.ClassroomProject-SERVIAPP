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


    @Column(name="consumos_elevados", nullable = false, length = 1000)
    private String consumo_alto_a;

    @Column (name="tipo_consumo", nullable=false,length = 50)
    private String tipo_de_consumo;


    @Column(name = "consumos_moderados", nullable = false, length = 1000)
        private String consumo_regular_a;

    @Column(name="consumos_bajos", nullable = false, length = 1000 )
    private String consumo_bueno_a;

 // rwlacion muchos a uno, un consejo pertenece a un administrador
    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private AdminModel administrador;

// relacion muchos a uno con periodos
    @ManyToOne
    @JoinColumn(name = "periodos_id", nullable = false)
    private PeriodoModel periodo;


    public ConsejosModel() {
    }

    public ConsejosModel(long id, String consumo_alto_a, String tipo_de_consumo, String consumo_regular_a,
            String consumo_bueno_a, AdminModel administrador, PeriodoModel periodo) {
        this.id = id;
        this.consumo_alto_a = consumo_alto_a;
        this.tipo_de_consumo = tipo_de_consumo;
        this.consumo_regular_a = consumo_regular_a;
        this.consumo_bueno_a = consumo_bueno_a;
        this.administrador = administrador;
        this.periodo = periodo;
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



    public String getTipo_de_consumo() {
        return tipo_de_consumo;
    }


    public void setTipo_de_consumo(String tipo_de_consumo) {
        this.tipo_de_consumo = tipo_de_consumo;
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




    public AdminModel getAdministrador() {
        return administrador;
    }



    public void setAdministrador(AdminModel administrador) {
        this.administrador = administrador;
    }



    public PeriodoModel getPeriodo() {
        return periodo;
    }


    public void setPeriodo(PeriodoModel periodo) {
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ConsejosModel{");
        sb.append("id=").append(id);
        sb.append(", consumo_alto_a=").append(consumo_alto_a);
        sb.append(", tipo_de_consumo=").append(tipo_de_consumo);
        sb.append(", consumo_regular_a=").append(consumo_regular_a);
        sb.append(", consumo_bueno_a=").append(consumo_bueno_a);
        sb.append(", administrador=").append(administrador);
        sb.append(", periodo=").append(periodo);
        sb.append('}');
        return sb.toString();
    }








   
   
}