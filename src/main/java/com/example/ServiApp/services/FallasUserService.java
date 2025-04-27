package com.example.ServiApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.Falla_Ser_Model;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.FallasRepository;
import com.example.ServiApp.repository.UsuarioRepository;

@Service
public class FallasUserService {

    @Autowired
    private FallasRepository fallasRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Falla_Ser_Model reportarFalla(Falla_Ser_Model falla, UsuarioModel usuario) {
        // Establecer la referencia al usuario
        falla.setUsuarioId(usuario.getId());
        
        // Guardar la falla
        Falla_Ser_Model fallaSaved = fallasRepository.save(falla);
        
        // Actualizar la referencia en el usuario
        usuario.getFallasIds().add(fallaSaved.getId());
        usuarioRepository.save(usuario);
        
        return fallaSaved;
    }

    // Método para obtener fallas
    public List<Falla_Ser_Model> obtenerFallas() {
        return fallasRepository.findAll();
    }
    
    public List<Falla_Ser_Model> obtenerFallasPorUsuario(String usuarioId) {
        return fallasRepository.findByUsuarioId(usuarioId);
    }
}
