package com.example.ServiApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.Falla_Ser_Model;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.FallasRepository;

@Service
public class FallasUserService {



     @Autowired
    private FallasRepository fallasRepository;

    public Falla_Ser_Model reportarFalla(Falla_Ser_Model falla, UsuarioModel usuario) {
        falla.setUsuario(usuario);
        return fallasRepository.save(falla);
    }
    
}
