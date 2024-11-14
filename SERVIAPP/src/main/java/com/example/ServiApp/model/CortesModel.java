package com.example.ServiApp.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cortes")
public class CortesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tipo_servicio", nullable = false, length = 50)
    private String tipo_servicio;

    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "motivo", nullable = false, length = 100)
    private String motivo;

    @Column(name = "barrios", nullable = false)
    private String barrios;  

    @Column(name = "hora_inicio", nullable = false)
    private String hora_inicio;  

    @Column(name = "hora_final", nullable = false)
    private String hora_final;   

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private AdminModel administrador;

    // Getters y Setters
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getBarrios() {
        return barrios;
    }

    // el metodo set para manejar la conversión de la lista de barrios a una cadena separada por comas
    public void setBarrios(List<String> barrios) {
        this.barrios = String.join(",", barrios);
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_final() {
        return hora_final;
    }

    public void setHora_final(String hora_final) {
        this.hora_final = hora_final;
    }

    public AdminModel getAdministrador() {
        return administrador;
    }

    public void setAdministrador(AdminModel administrador) {
        this.administrador = administrador;
    }
}
