package com.example.ServiApp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Modelo unificado para usuarios y administradores.
 * La diferenciación entre tipos se realiza mediante el campo rol.
 */
@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre_completo", nullable = false, length = 50)
    private String nombre;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    /**
     * Enumeración que define los roles disponibles en el sistema.
     * Permite distinguir entre usuarios normales y administradores.
     */
    public enum Rol {
        ROLE_USUARIO,
        ROLE_ADMINISTRADOR
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol;
    
    /**
     * Enumeración que define los estados posibles de un usuario en el sistema.
     * Permite habilitar o deshabilitar cuentas de usuario.
     */
    public enum EstadoUsuario {
        HABILITADO,
        DESHABILITADO
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoUsuario estado = EstadoUsuario.HABILITADO; // Por defecto, todos los usuarios están habilitados

    // Campo estrato solo usado por usuarios con rol USUARIO
    @Column(name = "estrato", length = 50)
    private Integer estrato;

    @Column(name = "registro_completo", nullable = false)
    private boolean registroCompleto = true; // Por defecto true para usuarios normales

    // Relaciones específicas según el rol del usuario
    // Relaciones para usuarios normales
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ServicioModel> servicios;

    // Relación uno a muchos con fallas de servicios (solo para usuarios normales)
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Falla_Ser_Model> fallas_Servicio;

    // Relaciones para administradores
    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CortesModel> cortes;

    // Relación uno a muchos con consejos (solo para administradores)
    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ConsejosModel> consejos;

    // Relación uno a uno con el predio (opcional)
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private PredioModel predio;

    public UsuarioModel() {
    }

    // Constructor completo
    public UsuarioModel(long id, String nombre, String email, String password, Rol rol,
            Integer estrato, List<ServicioModel> servicios,
            List<Falla_Ser_Model> fallas_Servicio, List<CortesModel> cortes,
            List<ConsejosModel> consejos) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.estrato = estrato;
        this.servicios = servicios;
        this.fallas_Servicio = fallas_Servicio;
        this.cortes = cortes;
        this.consejos = consejos;
    }

    /**
     * Método factory para crear un usuario normal.
     * Establece automáticamente el rol como USUARIO.
     */
    public static UsuarioModel crearUsuario(String nombre, String email, String password, int estrato) {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(Rol.ROLE_USUARIO);
        usuario.setEstado(EstadoUsuario.HABILITADO);
        usuario.setEstrato(estrato);
        return usuario;
    }

    /**
     * Método factory para crear un administrador.
     * Establece automáticamente el rol como ADMINISTRADOR.
     */
    public static UsuarioModel crearAdministrador(String nombre, String email, String password) {
        UsuarioModel admin = new UsuarioModel();
        admin.setNombre(nombre);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRol(Rol.ROLE_ADMINISTRADOR);
        admin.setEstado(EstadoUsuario.HABILITADO);
        return admin;
    }

    // Getters y setters
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    /**
     * Método helper para verificar si un usuario es administrador.
     * 
     * @return true si el usuario tiene rol ADMINISTRADOR, false en caso contrario
     */
    public boolean esAdministrador() {
        return Rol.ROLE_ADMINISTRADOR.equals(this.rol);
    }

    public Integer getEstrato() {
        return estrato;
    }

    public void setEstrato(Integer estrato) {
        this.estrato = estrato;
    }

    public List<ServicioModel> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioModel> servicios) {
        this.servicios = servicios;
    }

    public List<Falla_Ser_Model> getFallas_Servicio() {
        return fallas_Servicio;
    }

    public void setFallas_Servicio(List<Falla_Ser_Model> fallas_Servicio) {
        this.fallas_Servicio = fallas_Servicio;
    }

    public List<CortesModel> getCortes() {
        return cortes;
    }

    public void setCortes(List<CortesModel> cortes) {
        this.cortes = cortes;
    }

    public List<ConsejosModel> getConsejos() {
        return consejos;
    }

    public void setConsejos(List<ConsejosModel> consejos) {
        this.consejos = consejos;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
    
    /**
     * Método helper para verificar si un usuario está habilitado.
     * 
     * @return true si el usuario está habilitado, false en caso contrario
     */
    public boolean estaHabilitado() {
        return EstadoUsuario.HABILITADO.equals(this.estado);
    }

    public boolean isRegistroCompleto() {
        return registroCompleto;
    }

    public void setRegistroCompleto(boolean registroCompleto) {
        this.registroCompleto = registroCompleto;
    }

    public PredioModel getPredio() {
        return predio;
    }

    public void setPredio(PredioModel predio) {
        this.predio = predio;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UsuarioModel{");
        sb.append("id=").append(id);
        sb.append(", nombre=").append(nombre);
        sb.append(", email=").append(email);
        sb.append(", rol=").append(rol);
        sb.append(", estado=").append(estado);

        if (rol == Rol.ROLE_USUARIO) {
            sb.append(", estrato=").append(estrato);
            sb.append(", servicios=").append(servicios != null ? servicios.size() : 0);
            sb.append(", fallas_Servicio=").append(fallas_Servicio != null ? fallas_Servicio.size() : 0);
        } else {
            sb.append(", cortes=").append(cortes != null ? cortes.size() : 0);
            sb.append(", consejos=").append(consejos != null ? consejos.size() : 0);
        }

        sb.append('}');
        return sb.toString();
    }
}
