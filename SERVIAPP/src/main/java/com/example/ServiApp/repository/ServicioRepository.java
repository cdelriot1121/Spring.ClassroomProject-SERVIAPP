package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.ServicioModel;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioModel, Long> {

}
