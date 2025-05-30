package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ServiApp.model.ConsejosModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.ConsejosService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ConsejosController {

    @Autowired
    private ConsejosService consejosService;

    @PostMapping("/registrar-consejo")
    public String registrarConsejo(@ModelAttribute ConsejosModel consejo, 
                                Model model, 
                                HttpSession sessionadmin) {
        UsuarioModel adminLogueado = (UsuarioModel) sessionadmin.getAttribute("adminLogueado");

        if (adminLogueado != null && adminLogueado.esAdministrador()) {
            // Establecer la referencia al administrador
            consejo.setAdministradorId(adminLogueado.getId());
            
            // Guardar el consejo
            consejosService.registrarConsejo(consejo);

            model.addAttribute("mensaje-consejoreg", "Consejo registrado exitosamente");
            return "redirect:/consejos-admin";
        } else {
            model.addAttribute("error-consejo", "No hay administrador autenticado");
            return "redirect:/login-admin";
        }  
    }
}
