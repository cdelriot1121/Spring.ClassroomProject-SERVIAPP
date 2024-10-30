package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ServiApp.model.Falla_Ser_Model;

public interface FallasRepository extends JpaRepository<Falla_Ser_Model, Long> {

}
