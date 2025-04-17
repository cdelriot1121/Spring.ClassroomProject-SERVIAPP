package com.example.ServiApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.repository.PredioRepository; // Asegúrate de tener esta importación

import java.util.List;
import java.util.Optional;

@Service
public class PredioService {

    @Autowired
    private PredioRepository predioRepository;

    public List<PredioModel> obtenerTodosLosPredios() {
        return predioRepository.findAll(); // Cambiado a predioRepository
    }

    public Optional<PredioModel> obtenerPredioPorId(Long id) {
        return predioRepository.findById(id); // Cambiado a predioRepository
    }

    public List<PredioModel> obtenerPrediosPorUsuario(Long usuarioId) {
        return predioRepository.findByUsuarioId(usuarioId); // Cambiado a predioRepository
    }

    public PredioModel guardarPredio(PredioModel predio) {
        return predioRepository.save(predio); // Corregido el error tipográfico
    }

    public void eliminarPredio(Long id) {
        predioRepository.deleteById(id); // Cambiado a predioRepository
    }
}