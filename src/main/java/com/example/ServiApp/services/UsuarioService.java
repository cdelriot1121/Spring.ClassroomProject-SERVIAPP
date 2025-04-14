package com.example.ServiApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    // Guardar o actualizar un usuario (si ya está encriptado, ojo con eso)
    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public long contarUsuarios() {
        return usuarioRepository.count();
    }

    public Optional<UsuarioModel> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public List<UsuarioModel> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public boolean emailYaRegistrado(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    // Autenticación manual (por si la necesitás fuera de Spring Security)
    public UsuarioModel autenticar(String email, String contraseña) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(contraseña, u.getPassword()))
                .orElse(null);
    }

    // Autenticación solo para administradores (con contraseña hasheada)
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
}
