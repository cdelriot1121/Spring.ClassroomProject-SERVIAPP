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


  
    public Optional<UsuarioModel> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioModel guardarUsuario(UsuarioModel usuario){
        return usuarioRepository.save(usuario);
    }

  
  //Mismos metodos pero instaciados de manera dirente
    public List<UsuarioModel> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
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
