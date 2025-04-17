package com.example.ServiApp.controller;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.PredioService;
import com.example.ServiApp.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PredioController {

    @Autowired
    private PredioService predioService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registrar-predio")
    public String mostrarFormularioRegistro(Model model, Authentication authentication) {
        // Crear un nuevo predio para el formulario
        model.addAttribute("predio", new PredioModel());

        // Obtener el email del usuario autenticado
        String email = authentication.getName();  // Si usas Spring Security, esto obtiene el email del usuario

        // Obtener el usuario correspondiente al email
        UsuarioModel usuarioActual = usuarioService.obtenerUsuarioPorEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Añadir el usuario al modelo
        model.addAttribute("usuario", usuarioActual);

        return "registrar_predios"; // Nombre de la vista
    }

    // Procesar formulario
    @PostMapping("/registrar-predio")
    public String registrarPredio(@ModelAttribute PredioModel predio, Authentication authentication) {
        // Obtener el email del usuario autenticado
        String email = authentication.getName();  // Si usas Spring Security, esto obtiene el email del usuario

        // Obtener el usuario correspondiente al email
        UsuarioModel usuarioActual = usuarioService.obtenerUsuarioPorEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Asignar el usuario al predio
        predio.setUsuario(usuarioActual);

        // Guardar el predio
        predioService.guardarPredio(predio);

        return "redirect:/registrar-predio";  
    }
}
