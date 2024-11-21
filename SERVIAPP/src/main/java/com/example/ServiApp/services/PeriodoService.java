package com.example.ServiApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.PeriodoModel;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.repository.PeriodoRepository;

@Service
public class PeriodoService {
    @Autowired
    private PeriodoRepository periodoRepository;

    public PeriodoModel registrarPeriodo(PeriodoModel periodo){
        return periodoRepository.save(periodo);
    }

    public List<PeriodoModel> obtenerPeriodosPorServicios(ServicioModel servicio){
        return periodoRepository.findByServicio(servicio);
    }

    public List<PeriodoModel> obtenerPeriodos(){
        List<PeriodoModel> periodos = periodoRepository.findAll();
        return periodos;
    }

    
    


}