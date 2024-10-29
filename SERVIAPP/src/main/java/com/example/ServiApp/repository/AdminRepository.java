package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ServiApp.model.AdminModel;

public interface AdminRepository extends JpaRepository<AdminModel, Long>{

}
