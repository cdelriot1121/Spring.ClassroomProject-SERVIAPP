package com.example.ServiApp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

@Service
public class PredioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean existePredioParaUsuario(String usuarioId) {
        return usuarioRepository.findById(usuarioId)
            .map(usuario -> usuario.getPredio() != null)
            .orElse(false);
    }
    
    public Optional<PredioModel> obtenerPredioPorUsuario(String usuarioId) {
        return usuarioRepository.findById(usuarioId)
            .map(UsuarioModel::getPredio);
    }
    
    public void registrarPredioPara(String usuarioId, PredioModel predio) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setPredio(predio);
            usuarioRepository.save(usuario);
        }
    }
    
    public void actualizarPredio(String usuarioId, PredioModel predio) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setPredio(predio);
            usuarioRepository.save(usuario);
        }
    }
    
    public void eliminarPredio(String usuarioId) {
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setPredio(null);
            usuarioRepository.save(usuario);
        }
    }
}