package com.example.ServiApp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<ConsejosModel> consejos = consejoRepository.findByTipoServicioAndCategoriaConsumo(tipo_servicio, categoria);
        if (consejos.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron consejos para los criterios especificados.");
        }
        return consejos;
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
