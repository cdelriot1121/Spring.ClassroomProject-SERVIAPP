package com.example.ServiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioModel, Long> {

    List<ServicioModel> findByUsuario(UsuarioModel usuario);

}
