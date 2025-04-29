package com.example.ServiApp.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "usuarios")
public class UsuarioModel {

    @Id
    private String id;

    @Field(name = "nombre_completo")
    private String nombre;

    @Field(name = "email")
    private String email;

    @Field(name = "password")
    private String password;

    /**
     * Enumeración que define los roles disponibles en el sistema.
     */
    public enum Rol {
        ROLE_USUARIO,
        ROLE_ADMINISTRADOR
    }

    @Field(name = "rol")
    private Rol rol;
    
    /**
     * Enumeración que define los estados posibles de un usuario en el sistema.
     */
    public enum EstadoUsuario {
        HABILITADO,
        DESHABILITADO
    }
    
    @Field(name = "estado")
    private EstadoUsuario estado = EstadoUsuario.HABILITADO;

    @Field(name = "registro_completo")
    private boolean registroCompleto = true;

    // Documento embebido - predio
    @Field(name = "predio")
    private PredioModel predio;
    
    // Documentos embebidos - servicios
    @Field(name = "servicios")
    private List<ServicioModel> servicios = new ArrayList<>();
    
    // Referencias a otros documentos
    private List<String> fallasIds = new ArrayList<>();
    private List<String> cortesIds = new ArrayList<>();
    private List<String> consejosIds = new ArrayList<>();

    // Constructores, getters y setters

    public UsuarioModel() {
    }

    /**
     * Método factory para crear un usuario normal.
     */
    public static UsuarioModel crearUsuario(String nombre, String email, String password) {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(Rol.ROLE_USUARIO);
        usuario.setEstado(EstadoUsuario.HABILITADO);
        return usuario;
    }

    /**
     * Método factory para crear un administrador.
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public boolean esAdministrador() {
        return Rol.ROLE_ADMINISTRADOR.equals(this.rol);
    }

    public List<ServicioModel> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioModel> servicios) {
        this.servicios = servicios;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }
    
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

    public Integer getEstratoDesdePredio() {
        return this.predio != null ? this.predio.getEstrato() : null;
    }

    // Métodos para manejar las listas de IDs
    public List<String> getFallasIds() {
        return fallasIds;
    }

    public void setFallasIds(List<String> fallasIds) {
        this.fallasIds = fallasIds;
    }

    public List<String> getCortesIds() {
        return cortesIds;
    }

    public void setCortesIds(List<String> cortesIds) {
        this.cortesIds = cortesIds;
    }

    public List<String> getConsejosIds() {
        return consejosIds;
    }

    public void setConsejosIds(List<String> consejosIds) {
        this.consejosIds = consejosIds;
    }

    // Métodos para gestionar servicios
    public void addServicio(ServicioModel servicio) {
        if (this.servicios == null) {
            this.servicios = new ArrayList<>();
        }
        this.servicios.add(servicio);
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
            sb.append(", estrato=").append(getEstratoDesdePredio());
            sb.append(", servicios=").append(servicios != null ? servicios.size() : 0);
            sb.append(", fallas=").append(fallasIds != null ? fallasIds.size() : 0);
        } else {
            sb.append(", cortes=").append(cortesIds != null ? cortesIds.size() : 0);
            sb.append(", consejos=").append(consejosIds != null ? consejosIds.size() : 0);
        }

        sb.append('}');
        return sb.toString();
    }
}
