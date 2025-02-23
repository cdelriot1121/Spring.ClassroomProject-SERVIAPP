package com.example.ServiApp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="servicios")
public class ServicioModel {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    // Relación uno a muchos con periodos
    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PeriodoModel> periodos;

    @Column(name = "tipo_servicio", nullable = false, length = 50)
    private String tipoServicio;

    @Column(name = "empresa", nullable = false, length = 50)
    private String empresa;

    @Column(name = "poliza", nullable = false)
    private long poliza;

    @Column(name = "habitantes_hogar", nullable = false)
    private long habitantes;

    public ServicioModel() {
    }

    public ServicioModel(long id, UsuarioModel usuario, List<PeriodoModel> periodos,
                         String tipo_servicio, String empresa, long poliza, long habitantes) {
        this.id = id;
        this.usuario = usuario;
        this.periodos = periodos;
        this.tipoServicio = tipo_servicio;
        this.empresa = empresa;
        this.poliza = poliza;
        this.habitantes = habitantes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public List<PeriodoModel> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<PeriodoModel> periodos) {
        this.periodos = periodos;
    }

    public String getTipo_servicio() {
        return tipoServicio;
    }

    public void setTipo_servicio(String tipo_servicio) {
        this.tipoServicio = tipo_servicio;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public long getPoliza() {
        return poliza;
    }

    public void setPoliza(long poliza) {
        this.poliza = poliza;
    }

    public long getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(long habitantes) {
        this.habitantes = habitantes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ServicioModel{");
        sb.append("id=").append(id);
        sb.append(", usuarioId=").append(usuario != null ? usuario.getId() : null); // Evitar referencia recursiva
        sb.append(", periodos=").append(periodos != null ? periodos.size() : 0); // Mostrar cantidad de periodos
        sb.append(", tipo_servicio=").append(tipoServicio);
        sb.append(", empresa=").append(empresa);
        sb.append(", poliza=").append(poliza);
        sb.append(", habitantes=").append(habitantes);
        sb.append('}');
        return sb.toString();
    }
}
