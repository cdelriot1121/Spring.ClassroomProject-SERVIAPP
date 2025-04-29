package com.example.ServiApp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.ConsejosModel;
import com.example.ServiApp.model.PeriodoModel;
import com.example.ServiApp.repository.ConsejosRepository;
import com.example.ServiApp.repository.PeriodoRepository;

@Service
public class ConsejosService {

    @Autowired
    private ConsejosRepository consejoRepository;
    
    @Autowired
    private PeriodoRepository periodoRepository;

    public ConsejosModel registrarConsejo(ConsejosModel consejo) {
        return consejoRepository.save(consejo);
    }

    // Método para obtener todos los consejos
    public List<ConsejosModel> obtenerConsejos() {
        return consejoRepository.findAll();
    }

    public List<ConsejosModel> obtenerConsejosTipoServ_TipoCateg(String categoria, String tipo_servicio) {
        try {
            // Normalizar los parámetros para mejor búsqueda
            String categoriaNormalizada = categoria.trim().toLowerCase();
            String tipoServicioNormalizado = tipo_servicio.trim().toLowerCase();
            
            System.out.println("Buscando consejos con categoría: [" + categoriaNormalizada + "] y tipo servicio: [" + tipoServicioNormalizado + "]");
            
            // Primera búsqueda exacta
            List<ConsejosModel> consejos = consejoRepository.findByTipoServicioAndCategoriaConsumo(
                tipoServicioNormalizado, categoriaNormalizada);
            
            if (consejos.isEmpty()) {
                // Segunda búsqueda más flexible usando contains
                consejos = consejoRepository.findAll().stream()
                    .filter(c -> c.getTipoServicio().toLowerCase().contains(tipoServicioNormalizado) && 
                               c.getCategoriaConsumo().toLowerCase().contains(categoriaNormalizada))
                    .collect(Collectors.toList());
                
                System.out.println("Búsqueda alternativa encontró: " + consejos.size() + " consejos");
            }
            
            if (consejos.isEmpty()) {
                System.out.println("No se encontraron consejos, buscando por tipo de servicio...");
                // Tercera opción: buscar consejos solo por tipo de servicio
                consejos = consejoRepository.findByTipoServicio(tipoServicioNormalizado);
            }
            
            return consejos;
        } catch (Exception e) {
            System.err.println("Error en la búsqueda de consejos: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<ConsejosModel> obtenerConsejosPorPeriodo(String periodoId) {
        Optional<PeriodoModel> periodoOpt = periodoRepository.findById(periodoId);
        if (periodoOpt.isPresent()) {
            PeriodoModel periodo = periodoOpt.get();
            List<String> consejoIds = periodo.getConsejosRefs().stream()
                    .map(ref -> ref.getConsejoId())
                    .collect(Collectors.toList());
            
            if (!consejoIds.isEmpty()) {
                return consejoRepository.findByIdIn(consejoIds);
            }
        }
        return List.of(); // Lista vacía si no encuentra el periodo o no tiene consejos
    }
    
    // Método para asignar un consejo a un periodo
    public void asignarConsejoAPeriodo(String periodoId, String consejoId) {
        Optional<PeriodoModel> periodoOpt = periodoRepository.findById(periodoId);
        if (periodoOpt.isPresent() && consejoRepository.existsById(consejoId)) {
            PeriodoModel periodo = periodoOpt.get();
            periodo.addConsejoRef(consejoId);
            periodoRepository.save(periodo);
        } else {
            throw new IllegalArgumentException("Periodo o consejo no encontrados");
        }
    }
}
