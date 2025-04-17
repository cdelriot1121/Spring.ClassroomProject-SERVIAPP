package com.example.ServiApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioInhabilitadoController {
    
    @GetMapping("/error/usuario-inhabilitado")
    public String mostrarPaginaUsuarioInhabilitado(Model model) {
        // Asegurarse de que el mensaje sea claro y completo
        model.addAttribute("errorMessage", "Por favor, contáctanos para más información.");
        
        // No usar redirect: aquí, devolver directamente la vista
        return "error/usuario-inhabilitado";
    }
}