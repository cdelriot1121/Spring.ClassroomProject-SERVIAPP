package com.example.ServiApp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.PeriodoModel;

@Repository
public interface PeriodoRepository extends MongoRepository<PeriodoModel, String> {
    List<PeriodoModel> findByServicioId(String servicioId);
    
    List<PeriodoModel> findByUsuarioId(String usuarioId);
    
    // Usa Boolean (objeto) en lugar de boolean (primitivo)
    // O usar exists sin la anotación @Query
    boolean existsByUsuarioIdAndMesAndAnoAndServicioId(String usuarioId, String mes, int ano, String servicioId);
    
    // Método alternativo para verificar existencia
    List<PeriodoModel> findByUsuarioIdAndMesAndAnoAndServicioId(String usuarioId, String mes, int ano, String servicioId);
}
