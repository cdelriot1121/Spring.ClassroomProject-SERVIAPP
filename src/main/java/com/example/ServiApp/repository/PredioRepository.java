package com.example.ServiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.PredioModel;

@Repository
public interface PredioRepository extends JpaRepository<PredioModel, Long> {

    List<PredioModel> findByUsuarioId(Long usuarioId);

}