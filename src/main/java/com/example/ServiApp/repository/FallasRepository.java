package com.example.ServiApp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.Falla_Ser_Model;

@Repository
public interface FallasRepository extends MongoRepository<Falla_Ser_Model, String> {
    List<Falla_Ser_Model> findByUsuarioId(String usuarioId);
}
