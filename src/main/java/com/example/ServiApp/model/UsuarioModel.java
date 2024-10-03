package com.example.ServiApp.model;

public class UsuarioModel {

    private int id_usuario;
    private String nombre;
    private String email;
    private String contrasenia;
    private int estrato;
    private int no_serv_reg;

    
    //Constructures Usuario
    public UsuarioModel() {
    }

    public UsuarioModel(int id_usuario, String nombre, String email, String contrasenia, int estrato, int no_serv_reg) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.estrato = estrato;
        this.no_serv_reg = no_serv_reg;
    }


    //Getter and Setters
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public int getNo_serv_reg() {
        return no_serv_reg;
    }

    public void setNo_serv_reg(int no_serv_reg) {
        this.no_serv_reg = no_serv_reg;
    }
    


}
