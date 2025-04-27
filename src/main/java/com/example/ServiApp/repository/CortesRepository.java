package com.example.ServiApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.CortesModel;

@Repository
public interface CortesRepository extends MongoRepository<CortesModel, String> {
    // Métodos de consulta específicos pueden agregarse aquí
}


