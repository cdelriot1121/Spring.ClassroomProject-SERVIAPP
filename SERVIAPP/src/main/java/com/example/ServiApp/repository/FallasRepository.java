package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.Falla_Ser_Model;

@Repository
public interface FallasRepository extends JpaRepository<Falla_Ser_Model, Long> {

}
