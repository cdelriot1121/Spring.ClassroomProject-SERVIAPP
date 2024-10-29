package com.example.ServiApp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="admin_register")
public class AdminModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "usuario", nullable = false, length = 50)
    private String usuario;
        
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "PIN", nullable = false, length = 50)
    private String pin;

    public AdminModel() {
    }

    public AdminModel(long id, String usuario, String password, String pin) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.pin = pin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("adminModel{");
        sb.append("id=").append(id);
        sb.append(", usuario=").append(usuario);
        sb.append(", password=").append(password);
        sb.append(", pin=").append(pin);
        sb.append('}');
        return sb.toString();
    }



  







}
