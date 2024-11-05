package com.example.ServiApp.model;

import java.sql.Date;
import java.sql.Time;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="cortes")
public class CortesModel {


 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tipo_servicio", nullable = false, length = 50)
    private Date tipo_servicio;

    @Column(name = "Fecha", nullable = false, length = 50)
    private Date fecha;

    @Column (name = "motivo",nullable = false, length = 100 )
    private String motivo;

    @Column (name= "barrio", nullable=false, length=50)
    private String barrio;

    @Column (name="hora_inicio", nullable=false, length = 50)
    private Time hora_inicio;

    @Column(name="hora_final",nullable=false, length = 50)
    private Time hora_final;



    // relacion muchos a uno, un corte pertenece a un administrador
    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private AdminModel administrador;

    public CortesModel() {
    }

    public CortesModel(long id, Date tipo_servicio, Date fecha, String motivo, String barrio, Time hora_inicio,
            Time hora_final, AdminModel administrador) {
        this.id = id;
        this.tipo_servicio = tipo_servicio;
        this.fecha = fecha;
        this.motivo = motivo;
        this.barrio = barrio;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.administrador = administrador;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTipo_servicio() {
        return tipo_servicio;
    }

    public void setTipo_servicio(Date tipo_servicio) {
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

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public Time getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(Time hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public Time getHora_final() {
        return hora_final;
    }

    public void setHora_final(Time hora_final) {
        this.hora_final = hora_final;
    }

    public AdminModel getAdministrador() {
        return administrador;
    }

    public void setAdministrador(AdminModel administrador) {
        this.administrador = administrador;
    }

    @Override
    public String toString() {
        return "CortesModel [id=" + id + ", tipo_servicio=" + tipo_servicio + ", fecha=" + fecha + ", motivo=" + motivo
                + ", barrio=" + barrio + ", hora_inicio=" + hora_inicio + ", hora_final=" + hora_final
                + ", administrador=" + administrador + "]";
    }

}
