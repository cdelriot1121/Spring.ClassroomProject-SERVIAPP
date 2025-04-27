package com.example.ServiApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.CortesModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.CortesRepository;
import com.example.ServiApp.repository.UsuarioRepository;

@Service
public class CortesService {
    
    @Autowired
    private CortesRepository cortesRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public CortesModel registrarCorte(CortesModel corte) {
        // Guardar el corte
        CortesModel corteSaved = cortesRepository.save(corte);
        
        // Actualizar la referencia en el administrador
        UsuarioModel admin = usuarioRepository.findById(corte.getAdministradorId()).orElse(null);
        if (admin != null) {
            admin.getCortesIds().add(corteSaved.getId());
            usuarioRepository.save(admin);
        }
        
        return corteSaved;
    }

    public List<CortesModel> obtenerTodosLosCortes() {
        return cortesRepository.findAll();
    }
    
    public List<CortesModel> obtenerCortesPorAdministrador(String administradorId) {
        return cortesRepository.findByAdministradorId(administradorId);
    }
    
    public void eliminarCorte(String id) {
        // Primero, encontrar el corte para obtener el administradorId
        CortesModel corte = cortesRepository.findById(id).orElse(null);
        if (corte != null) {
            // Eliminar la referencia en el administrador
            UsuarioModel admin = usuarioRepository.findById(corte.getAdministradorId()).orElse(null);
            if (admin != null) {
                admin.getCortesIds().removeIf(corteId -> corteId.equals(id));
                usuarioRepository.save(admin);
            }
            
            // Eliminar el corte
            cortesRepository.deleteById(id);
        }
    }
}
