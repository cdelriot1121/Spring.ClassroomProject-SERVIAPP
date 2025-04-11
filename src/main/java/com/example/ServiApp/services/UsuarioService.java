package com.example.ServiApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Métodos generales
    public UsuarioModel registrarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public long contarUsuarios() {
        return usuarioRepository.count();
    }
    
    public Optional<UsuarioModel> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public List<UsuarioModel> obtenerUsuarios(){
        return usuarioRepository.findAll();
    }
    
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
    
    public boolean emailYaRegistrado(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    // Método para autenticar usuarios (generales)
    public UsuarioModel autenticar(String email, String contraseña) {
        return usuarioRepository.findAll().stream()
            .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(contraseña))
            .findFirst()
            .orElse(null);
    }
    
    // Métodos para usuarios normales
    public List<UsuarioModel> obtenerUsuariosNormales() {
        return usuarioRepository.findByRol(UsuarioModel.Rol.USUARIO);
    }
    
    // Métodos para administradores
    public UsuarioModel autenticarAdministrador(String email, String contraseña) {
        return usuarioRepository.findAll().stream()
            .filter(u -> u.getRol() == UsuarioModel.Rol.ADMINISTRADOR && 
                   u.getEmail().equals(email) &&
                   u.getPassword().equals(contraseña))
            .findFirst()
            .orElse(null);
    }
    
    public List<UsuarioModel> obtenerAdministradores() {
        return usuarioRepository.findByRol(UsuarioModel.Rol.ADMINISTRADOR);
    }
}
