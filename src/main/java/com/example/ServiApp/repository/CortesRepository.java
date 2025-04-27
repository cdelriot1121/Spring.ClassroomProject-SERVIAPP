package com.example.ServiApp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.CortesModel;

@Repository
public interface CortesRepository extends MongoRepository<CortesModel, String> {
    List<CortesModel> findByAdministradorId(String administradorId);
}


