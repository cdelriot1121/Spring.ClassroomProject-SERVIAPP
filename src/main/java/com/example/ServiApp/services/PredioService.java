package com.example.ServiApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.PredioRepository;

@Service
public class PredioService {

    @Autowired
    private PredioRepository predioRepository;

    public PredioModel registrarPredio(PredioModel predio) {
        return predioRepository.save(predio);
    }
    
    public Optional<PredioModel> obtenerPredioPorUsuario(UsuarioModel usuario) {
        return predioRepository.findByUsuario(usuario);
    }
    
    public Optional<PredioModel> obtenerPredioPorId(Long id) {
        return predioRepository.findById(id);
    }
    
    public List<PredioModel> obtenerPrediosPorBarrio(String barrio) {
        return predioRepository.findByBarrio(barrio);
    }
    
    public List<PredioModel> obtenerTodosPredios() {
        return predioRepository.findAll();
    }
    
    public void actualizarPredio(PredioModel predio) {
        predioRepository.save(predio);
    }
    
    public void eliminarPredio(Long id) {
        predioRepository.deleteById(id);
    }
    
    public boolean existePredioParaUsuario(Long usuarioId) {
        return predioRepository.existsByUsuarioId(usuarioId);
    }
}