package com.example.ServiApp.services;

import java.util.List;
import java.util.Optional;

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

    public Optional<PeriodoModel> obtenerPeriodoPorId(Long id) {
        return periodoRepository.findById(id);
    }
    
    // Método para guardar un periodo (actualizar)
    public PeriodoModel guardarPeriodo(PeriodoModel periodo) {
        return periodoRepository.save(periodo);
    }
    
    // Método para eliminar un periodo
    public void eliminarPeriodo(Long id) {
        periodoRepository.deleteById(id);
    }
}