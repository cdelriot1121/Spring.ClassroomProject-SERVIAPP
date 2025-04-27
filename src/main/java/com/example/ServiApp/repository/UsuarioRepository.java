package com.example.ServiApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioModel, String> {
    Optional<UsuarioModel> findByEmail(String email);
    List<UsuarioModel> findByRol(UsuarioModel.Rol rol);
    boolean existsByEmail(String email);
}
