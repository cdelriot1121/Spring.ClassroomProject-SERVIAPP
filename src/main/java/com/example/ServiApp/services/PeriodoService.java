package com.example.ServiApp.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.PeriodoModel;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.PeriodoRepository;
import com.example.ServiApp.repository.UsuarioRepository;

@Service
public class PeriodoService {
    
    @Autowired
    private PeriodoRepository periodoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioService usuarioService;

    public PeriodoModel registrarPeriodo(PeriodoModel periodo) {
        return periodoRepository.save(periodo);
    }

    public List<PeriodoModel> obtenerPeriodosPorServicio(String servicioId) {
        return periodoRepository.findByServicioId(servicioId);
    }
    
    public List<PeriodoModel> obtenerPeriodosPorUsuario(String usuarioId) {
        return periodoRepository.findByUsuarioId(usuarioId);
    }
    
    public List<PeriodoModel> obtenerPeriodos() {
        return periodoRepository.findAll();
    }

    public Optional<PeriodoModel> obtenerPeriodoPorId(String id) {
        return periodoRepository.findById(id);
    }
    
    // Validar que no haya duplicaciones de mes y año para el mismo servicio
    public boolean existePeriodoRegistrado(String usuarioId, String mes, int ano, String servicioId) {
        try {
            // Usamos el método alternativo que es más seguro
            List<PeriodoModel> periodos = periodoRepository.findByUsuarioIdAndMesAndAnoAndServicioId(
                usuarioId, mes, ano, servicioId);
            return periodos != null && !periodos.isEmpty();
        } catch (Exception e) {
            System.err.println("Error al verificar existencia del periodo: " + e.getMessage());
            e.printStackTrace();
            return false; // Por defecto asumimos que no existe en caso de error
        }
    }
    
    // Guardar un periodo (actualizar)
    public PeriodoModel guardarPeriodo(PeriodoModel periodo) {
        return periodoRepository.save(periodo);
    }
    
    // Eliminar un periodo
    public void eliminarPeriodo(String id) {
        periodoRepository.deleteById(id);
        
        // También eliminamos la referencia en el servicio correspondiente
        String servicioId = periodoRepository.findById(id)
            .map(PeriodoModel::getServicioId)
            .orElse(null);
            
        if (servicioId != null) {
            // Buscamos el usuario que contiene este servicio
            List<UsuarioModel> usuarios = usuarioRepository.findAll();
            for (UsuarioModel usuario : usuarios) {
                for (ServicioModel servicio : usuario.getServicios()) {
                    if (servicio.getId().equals(servicioId)) {
                        servicio.getPeriodosIds().removeIf(pId -> pId.equals(id));
                        usuarioRepository.save(usuario);
                        break;
                    }
                }
            }
        }
    }
    
    // Obtener todos los periodos para todos los servicios de un usuario
    public List<PeriodoModel> obtenerTodosPeriodosPorUsuario(String usuarioId) {
        List<ServicioModel> servicios = usuarioService.obtenerServiciosPorUsuario(usuarioId);
        
        // Buscar periodos para cada servicio
        return servicios.stream()
                .flatMap(servicio -> obtenerPeriodosPorServicio(servicio.getId()).stream())
                .collect(Collectors.toList());
    }
}