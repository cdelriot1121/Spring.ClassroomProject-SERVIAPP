package com.example.ServiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.PeriodoModel;
import com.example.ServiApp.model.ServicioModel;

@Repository

public interface PeriodoRepository extends JpaRepository<PeriodoModel, Long> {

    List<PeriodoModel> findByServicio(ServicioModel servicio);
}
