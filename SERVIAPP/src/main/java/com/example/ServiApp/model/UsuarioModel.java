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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name= "usuarios", uniqueConstraints= @UniqueConstraint(columnNames = "email"))
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre_completo", nullable = false, length = 50)
    private String nombre;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "estrato", nullable = false, length = 50)
    private int estrato;

    // relacion uno a muchos con servicios, el usuario puede tener de uno a muchos servicios
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ServicioModel> servicios;


     // relacion uno a muchos con fallas de servicios, el usuario puede tener de uno a muchas fallas de servicio

    @OneToMany(mappedBy="usuario", cascade= CascadeType.ALL)
    @JsonIgnore
    private List<Falla_Ser_Model> Fallas_Servicio;


    public UsuarioModel() {
    }


    public UsuarioModel(long id, String nombre, String email, String password, int estrato,
            List<ServicioModel> servicios, List<Falla_Ser_Model> fallas_Servicio) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.estrato = estrato;
        this.servicios = servicios;
        Fallas_Servicio = fallas_Servicio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public List<ServicioModel> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioModel> servicios) {
        this.servicios = servicios;
    }

    public List<Falla_Ser_Model> getFallas_Servicio() {
        return Fallas_Servicio;
    }

    public void setFallas_Servicio(List<Falla_Ser_Model> Fallas_Servicio) {
        this.Fallas_Servicio = Fallas_Servicio;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UsuarioModel{");
        sb.append("id=").append(id);
        sb.append(", nombre=").append(nombre);
        sb.append(", email=").append(email);
        sb.append(", password=").append(password);
        sb.append(", estrato=").append(estrato);
        sb.append(", servicios=").append(servicios);
        sb.append(", Fallas_Servicio=").append(Fallas_Servicio);
        sb.append('}');
        return sb.toString();
    }








    
}
