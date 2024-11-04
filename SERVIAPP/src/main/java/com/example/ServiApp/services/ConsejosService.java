package com.example.ServiApp.services;

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


}
