package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.ConsejosModel;

@Repository
public interface ConsejosRepository extends JpaRepository<ConsejosModel, Long> {

}
