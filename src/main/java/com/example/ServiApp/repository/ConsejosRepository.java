package com.example.ServiApp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.ConsejosModel;

@Repository
public interface ConsejosRepository extends MongoRepository<ConsejosModel, String> {
    List<ConsejosModel> findByTipoServicioAndCategoriaConsumo(String tipoServicio, String categoriaConsumo);
    List<ConsejosModel> findByTipoServicio(String tipoServicio);
    List<ConsejosModel> findByIdIn(List<String> ids);
}
