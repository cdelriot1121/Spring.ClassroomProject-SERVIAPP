package com.example.ServiApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.ConsejosModel;


@Repository
public interface ConsejosRepository extends JpaRepository<ConsejosModel, Long> {
    Optional<ConsejosModel> findById(long id);
    List<ConsejosModel> findByTipoServicioAndCategoriaConsumo(String tipoServicio, String categoriaConsumo);
    List<ConsejosModel> findByPeriodos_Id(long periodoId);

}
