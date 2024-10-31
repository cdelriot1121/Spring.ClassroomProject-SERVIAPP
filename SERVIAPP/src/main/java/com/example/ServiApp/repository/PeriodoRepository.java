package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.PeriodoModel;

@Repository

public interface PeriodoRepository extends JpaRepository<PeriodoModel, Long> {

}
