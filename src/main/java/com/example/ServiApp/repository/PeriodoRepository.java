package com.example.ServiApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ServiApp.model.PeriodoModel;
import com.example.ServiApp.model.ServicioModel;

@Repository

public interface PeriodoRepository extends JpaRepository<PeriodoModel, Long> {

    List<PeriodoModel> findByServicio(ServicioModel servicio);
    //Valida si ya existe un analisis hecho por servicio, mes y año
    @Query("SELECT COUNT(p) > 0 FROM PeriodoModel p WHERE p.servicio.usuario.id = :idUsuario AND p.mes = :mes AND p.ano = :ano AND p.servicio.id = :idServicio")
boolean existsByUsuarioAndMesAndAnoAndServicio(@Param("idUsuario") Long idUsuario,
                                               @Param("mes") String mes,
                                               @Param("ano") int ano,
                                               @Param("idServicio") Long idServicio);

}
