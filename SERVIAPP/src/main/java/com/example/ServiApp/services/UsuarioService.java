package com.example.ServiApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

@Service
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioModel registrarUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public long contarUsuarios() {
        return usuarioRepository.count();
    }

    public UsuarioModel autenticar(String email, String contraseña){
        return usuarioRepository.findAll().stream()
        .filter(usuario -> usuario.getEmail().equals(email) &&
                           usuario.getPassword().equals(contraseña))
                           .findFirst()
                           .orElse(null);
    }


    public List<UsuarioModel> obtenerUsuarios(){
    List<UsuarioModel> usuarios = usuarioRepository.findAll();
    System.out.println("Usuarios encontrados: " + usuarios.size()); // Log para ver si se encuentran usuarios
    return usuarios;
    }

    // Método para eliminar un usuario
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id); // Usamos el método deleteById del repositorio
    }



}
