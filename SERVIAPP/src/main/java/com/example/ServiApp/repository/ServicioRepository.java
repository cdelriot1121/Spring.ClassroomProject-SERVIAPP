package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ServiApp.model.ServicioModel;

public interface ServicioRepository extends JpaRepository<ServicioModel, Long> {

}
