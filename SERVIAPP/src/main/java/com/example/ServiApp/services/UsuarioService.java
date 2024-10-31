package com.example.ServiApp.services;

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
}
