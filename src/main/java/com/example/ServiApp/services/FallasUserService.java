package com.example.ServiApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.Falla_Ser_Model;
import com.example.ServiApp.model.Falla_Ser_Model.EstadoFalla;
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
        
        // Asegurarnos que el estado sea PENDIENTE
        falla.setEstado(EstadoFalla.PENDIENTE);
        
        // Guardar la falla
        Falla_Ser_Model fallaSaved = fallasRepository.save(falla);
        
        // Actualizar la referencia en el usuario
        usuario.getFallasIds().add(fallaSaved.getId());
        usuarioRepository.save(usuario);
        
        return fallaSaved;
    }

    // Método para cambiar el estado de una falla
    public Falla_Ser_Model cambiarEstadoFalla(String fallaId, EstadoFalla nuevoEstado) {
        Optional<Falla_Ser_Model> fallaOptional = fallasRepository.findById(fallaId);
        
        if (fallaOptional.isPresent()) {
            Falla_Ser_Model falla = fallaOptional.get();
            falla.setEstado(nuevoEstado);
            return fallasRepository.save(falla);
        }
        
        return null;
    }

    // Método para obtener una falla por su ID
    public Optional<Falla_Ser_Model> obtenerFallaPorId(String fallaId) {
        return fallasRepository.findById(fallaId);
    }

    // Método para obtener fallas
    public List<Falla_Ser_Model> obtenerFallas() {
        return fallasRepository.findAll();
    }
    
    // Método existente para obtener fallas por usuario
    public List<Falla_Ser_Model> obtenerFallasPorUsuario(String usuarioId) {
        return fallasRepository.findByUsuarioId(usuarioId);
    }

    public Page<Falla_Ser_Model> obtenerFallasPaginadas(Pageable pageable) {
    return fallasRepository.findAll(pageable);
}


}
