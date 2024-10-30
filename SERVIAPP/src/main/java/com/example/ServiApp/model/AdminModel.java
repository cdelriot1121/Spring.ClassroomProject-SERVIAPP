package com.example.ServiApp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

 // relacion uno a muchos, un administrador puede reportar de uno mucho cortes
    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
     @JsonIgnore
    private java.util.List<CortesModel> cortes;

     // relacion uno a muchos, un administrador puede guardar de uno a muchos consejos
     @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
     @JsonIgnore
     private List<ConsejosModel> consejos;
 




    public AdminModel() {
    }





    public AdminModel(long id, String usuario, String password, String pin, List<CortesModel> cortes,
            List<ConsejosModel> consejos) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.pin = pin;
        this.cortes = cortes;
        this.consejos = consejos;
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

    public java.util.List<CortesModel> getCortes() {
        return cortes;
    }
    public void setCortes(java.util.List<CortesModel> cortes) {
        this.cortes = cortes;
    }

    public List<ConsejosModel> getConsejos() {
        return consejos;
    }


    public void setConsejos(List<ConsejosModel> consejos) {
        this.consejos = consejos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdminModel{");
        sb.append("id=").append(id);
        sb.append(", usuario=").append(usuario);
        sb.append(", password=").append(password);
        sb.append(", pin=").append(pin);
        sb.append(", cortes=").append(cortes);
        sb.append(", consejos=").append(consejos);
        sb.append('}');
        return sb.toString();
    }

 
}
