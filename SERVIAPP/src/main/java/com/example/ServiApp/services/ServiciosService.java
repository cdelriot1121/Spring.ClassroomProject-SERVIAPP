package com.example.ServiApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.ServicioRepository;

@Service
public class ServiciosService {


@Autowired
private ServicioRepository servicioRepository;

public ServicioModel registrarservicio(ServicioModel servicio){
    return servicioRepository.save(servicio);
}


    public List<ServicioModel> obtenerServiciosPorUsuario(UsuarioModel usuario) {
        return servicioRepository.findByUsuario(usuario);
    }
   
    public List<ServicioModel> ObtenerServicios(){
        List<ServicioModel> servicios = servicioRepository.findAll();
        return servicios;
        }

    public Optional<ServicioModel> obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id);
        }

    


    public void actualizarServicio(Long id, ServicioModel servicioActualizado){
        ServicioModel servicioExistente = servicioRepository.findById(id).orElseThrow(()->
        new IllegalArgumentException("Servicio no encontrado con ID: " + id)
            );
    
        servicioExistente.setPoliza(servicioActualizado.getPoliza());
        servicioExistente.setHabitantes(servicioActualizado.getHabitantes());
    
        servicioRepository.save(servicioExistente);
            
        }
    

    public void eliminarServicio(Long id){
        servicioRepository.deleteById(id);
    }


}
