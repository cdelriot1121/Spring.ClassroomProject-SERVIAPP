package com.example.ServiApp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.PeriodoModel;

@Repository
public interface PeriodoRepository extends MongoRepository<PeriodoModel, String> {
    List<PeriodoModel> findByServicioId(String servicioId);
    
    List<PeriodoModel> findByUsuarioId(String usuarioId);
    
    @Query("{'usuarioId': ?0, 'mes': ?1, 'ano': ?2, 'servicioId': ?3}")
    boolean existsByUsuarioIdAndMesAndAnoAndServicioId(String usuarioId, String mes, int ano, String servicioId);
}
