package com.example.ServiApp.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Registrar un nuevo usuario (con contraseña encriptada)
    public UsuarioModel registrarUsuario(UsuarioModel usuario) {
        // Validar si el email ya está registrado
        if (emailYaRegistrado(usuario.getEmail())) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado.");
        }

        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    // Guardar o actualizar un usuario
    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public long contarUsuarios() {
        return usuarioRepository.count();
    }

    public Optional<UsuarioModel> obtenerUsuarioPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public List<UsuarioModel> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public void inhabilitarUsuario(String id) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            usuario.setEstado(UsuarioModel.EstadoUsuario.DESHABILITADO);
            usuarioRepository.save(usuario);
        }
    }
    
    public void habilitarUsuario(String id) {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            UsuarioModel usuario = usuarioOptional.get();
            usuario.setEstado(UsuarioModel.EstadoUsuario.HABILITADO);
            usuarioRepository.save(usuario);
        }
    }

    public boolean emailYaRegistrado(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Autenticación manual
    public UsuarioModel autenticar(String email, String contraseña) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(contraseña, u.getPassword()))
                .orElse(null);
    }

    // Autenticación solo para administradores
    public UsuarioModel autenticarAdministrador(String email, String contraseña) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> u.getRol() == UsuarioModel.Rol.ROLE_ADMINISTRADOR &&
                        passwordEncoder.matches(contraseña, u.getPassword()))
                .orElse(null);
    }

    public List<UsuarioModel> obtenerUsuariosNormales() {
        return usuarioRepository.findByRol(UsuarioModel.Rol.ROLE_USUARIO);
    }

    public List<UsuarioModel> obtenerAdministradores() {
        return usuarioRepository.findByRol(UsuarioModel.Rol.ROLE_ADMINISTRADOR);
    }
    
    // ------------ Métodos para gestionar predios embebidos ------------
    
    public void registrarPredioPara(String usuarioId, PredioModel predio) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setPredio(predio);
            usuarioRepository.save(usuario);
        }
    }
    
    public Optional<PredioModel> obtenerPredioPorUsuario(String usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(UsuarioModel::getPredio);
    }
    
    public boolean existePredioParaUsuario(String usuarioId) {
        return obtenerPredioPorUsuario(usuarioId).isPresent();
    }
    
    public void actualizarPredio(String usuarioId, PredioModel predio) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setPredio(predio);
            usuarioRepository.save(usuario);
        }
    }
    
    // ------------ Métodos para gestionar servicios embebidos ------------
    
    public ServicioModel registrarServicio(String usuarioId, ServicioModel servicio) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            
            // Generar un ID único para el servicio
            servicio.setId(UUID.randomUUID().toString());
            
            usuario.addServicio(servicio);
            usuarioRepository.save(usuario);
            return servicio;
        }
        return null;
    }
    
    public List<ServicioModel> obtenerServiciosPorUsuario(String usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(UsuarioModel::getServicios)
                .orElse(Collections.emptyList());
    }
    
    public Optional<ServicioModel> obtenerServicioPorId(String usuarioId, String servicioId) {
        return usuarioRepository.findById(usuarioId)
                .flatMap(usuario -> usuario.getServicios().stream()
                        .filter(s -> s.getId().equals(servicioId))
                        .findFirst());
    }
    
    public boolean existeServicioPorTipoYUsuario(String tipoServicio, String usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .map(usuario -> usuario.getServicios().stream()
                        .anyMatch(s -> s.getTipo_servicio().equals(tipoServicio)))
                .orElse(false);
    }
    
    public void actualizarServicio(String usuarioId, String servicioId, ServicioModel servicioActualizado) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            List<ServicioModel> servicios = usuario.getServicios();
            
            for (int i = 0; i < servicios.size(); i++) {
                if (servicios.get(i).getId().equals(servicioId)) {
                    servicios.get(i).setPoliza(servicioActualizado.getPoliza());
                    servicios.get(i).setHabitantes(servicioActualizado.getHabitantes());
                    servicios.get(i).setEmpresa(servicioActualizado.getEmpresa());
                    break;
                }
            }
            
            usuario.setServicios(servicios);
            usuarioRepository.save(usuario);
        }
    }
    
    public void eliminarServicio(String usuarioId, String servicioId) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setServicios(usuario.getServicios().stream()
                    .filter(s -> !s.getId().equals(servicioId))
                    .collect(java.util.stream.Collectors.toList()));
            usuarioRepository.save(usuario);
        }
    }
    
    public List<ServicioModel> obtenerTodosLosServicios() {
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<ServicioModel> todosLosServicios = new ArrayList<>();
        
        for (UsuarioModel usuario : usuarios) {
            todosLosServicios.addAll(usuario.getServicios());
        }
        
        return todosLosServicios;
    }
    
    // Añadir este método al final de la clase UsuarioService
    public String encontrarUsuarioIdPorServicioId(String servicioId) {
        for (UsuarioModel usuario : usuarioRepository.findAll()) {
            for (ServicioModel servicio : usuario.getServicios()) {
                if (servicio.getId().equals(servicioId)) {
                    return usuario.getId();
                }
            }
        }
        return null;
    }
}
