package com.example.ServiApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.UsuarioModel;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
    Optional<UsuarioModel> findById(long id);
    
    Optional<UsuarioModel> findByEmail(String email);
    
    // Métodos para filtrar por rol
    List<UsuarioModel> findByRol(UsuarioModel.Rol rol);
    
    // Para autenticación de administradores (con email y pin)
    Optional<UsuarioModel> findByEmailAndPin(String email, String pin);
}
