package com.example.ServiApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.ConsejosModel;
import com.example.ServiApp.repository.ConsejosRepository;


@Service
public class ConsejosService {

    @Autowired
    private ConsejosRepository consejoRepository;

    public ConsejosModel registrarConsejo(ConsejosModel consejo) {
        return consejoRepository.save(consejo);
    }


    //metodo para obtener consejos que se utilizara a futuro
    public List<ConsejosModel> obtenerConsejos(){
        List<ConsejosModel> consejos = consejoRepository.findAll();
        return consejos;
    }

    public List<ConsejosModel> obtenerConsejosTipoServ_TipoCateg(String categoria, String tipo_servicio) {
        List<ConsejosModel> consejos = consejoRepository.findByTipoServicioAndCategoriaConsumo(tipo_servicio, categoria);
        if (consejos.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron consejos para los criterios especificados.");
        }
        return consejos;
    }

    public List<ConsejosModel> obtenerConsejosPorPeriodo(long periodoId) {
        return consejoRepository.findByPeriodos_Id(periodoId);
    }


}
