package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ServiApp.model.GestionServicioModel;

public interface GestionServicioRepository extends JpaRepository<GestionServicioModel, Long> {

}