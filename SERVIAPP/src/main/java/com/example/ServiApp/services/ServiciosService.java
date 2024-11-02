package com.example.ServiApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.repository.ServicioRepository;

@Service
public class ServiciosService {


@Autowired
private ServicioRepository servicioRepository;

public ServicioModel registrarservicio(ServicioModel servicio){
    return servicioRepository.save(servicio);
}


}
