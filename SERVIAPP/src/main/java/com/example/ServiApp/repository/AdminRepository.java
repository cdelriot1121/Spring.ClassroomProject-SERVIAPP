package com.example.ServiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.AdminModel;
@Repository
public interface AdminRepository extends JpaRepository<AdminModel, Long>{

}
