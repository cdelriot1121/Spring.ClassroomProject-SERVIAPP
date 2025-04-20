package com.example.ServiApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.UsuarioModel;

@Repository
public interface PredioRepository extends JpaRepository<PredioModel, Long> {
    
    Optional<PredioModel> findByUsuario(UsuarioModel usuario);
    
    List<PredioModel> findByBarrio(String barrio);
    
    boolean existsByUsuarioId(Long usuarioId);
}