package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ServiApp.model.CortesModel;

public interface CortesRepository extends JpaRepository<CortesModel, Long> {

}
