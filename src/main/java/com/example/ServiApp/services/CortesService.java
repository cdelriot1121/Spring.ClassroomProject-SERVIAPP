package com.example.ServiApp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.CortesModel;
import com.example.ServiApp.repository.CortesRepository;

@Service
public class CortesService {
    
    @Autowired
    private CortesRepository cortesRepository;

    public CortesModel registrarCorte(CortesModel corte){
        return cortesRepository.save(corte);
    }

    public List<CortesModel> obtenerTodosLosCortes() {
        return cortesRepository.findAll();
    }

}
