package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ServiApp.util.DataLoader;

@RestController
@RequestMapping("/admin/datos")
@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
public class DatosInicalesController {

    @Autowired
    private DataLoader dataLoader;
    
    @GetMapping("/cargar-consejos")
    public String cargarConsejos() {
        try {
            dataLoader.cargarDatosManuales();
            return "Consejos cargados correctamente";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al cargar consejos: " + e.getMessage();
        }
    }
}