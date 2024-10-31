package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.CortesModel;
@Repository
public interface CortesRepository extends JpaRepository<CortesModel, Long> {

}
