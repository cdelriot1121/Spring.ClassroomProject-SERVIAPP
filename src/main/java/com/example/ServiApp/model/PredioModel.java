package com.example.ServiApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "predios")
public class PredioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "barrio", length = 100)
    private String barrio;

    @Column(name = "direccion", length = 150)
    private String direccion;

    @Column(name = "estrato", nullable = false)
    private int estrato;

    /**
     * Enumeración que define los tipos de predio disponibles
     */
    public enum TipoPredio {
        CASA,
        APARTAMENTO
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_predio")
    private TipoPredio tipoPredio;

    // Relación con el usuario (un predio pertenece a un único usuario)
    @OneToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private UsuarioModel usuario;

    // Constructor vacío
    public PredioModel() {
    }

    // Constructor completo
    public PredioModel(long id, String barrio, String direccion, int estrato, TipoPredio tipoPredio, UsuarioModel usuario) {
        this.id = id;
        this.barrio = barrio;
        this.direccion = direccion;
        this.estrato = estrato;
        this.tipoPredio = tipoPredio;
        this.usuario = usuario;
    }

    // Getters y setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public TipoPredio getTipoPredio() {
        return tipoPredio;
    }

    public void setTipoPredio(TipoPredio tipoPredio) {
        this.tipoPredio = tipoPredio;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "PredioModel{" +
                "id=" + id +
                ", barrio='" + barrio + '\'' +
                ", direccion='" + direccion + '\'' +
                ", estrato=" + estrato +
                ", tipoPredio=" + tipoPredio +
                ", usuarioId=" + (usuario != null ? usuario.getId() : null) +
                '}';
    }
}