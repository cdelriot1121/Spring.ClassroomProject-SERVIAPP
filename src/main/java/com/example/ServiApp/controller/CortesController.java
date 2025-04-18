package com.example.ServiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ServiApp.model.CortesModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.CortesService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CortesController {

    @Autowired
    private CortesService cortesService;

    @PostMapping("/registrar-corte")
    public String registrarCorte(@ModelAttribute CortesModel corte,
                                  Model model,
                                  HttpSession sessionadmin) {

        // Obtener el administrador logueado
        UsuarioModel adminLogueado = (UsuarioModel) sessionadmin.getAttribute("adminLogueado");

        if (adminLogueado != null && adminLogueado.esAdministrador()) {
            corte.setAdministrador(adminLogueado); 

            // Obtener los barrios seleccionados como una lista de cadenas
            @SuppressWarnings("unchecked")
            List<String> barriosSeleccionados = (List<String>) sessionadmin.getAttribute("barriosSeleccionados");
            if (barriosSeleccionados != null) {
                corte.setBarrios(barriosSeleccionados);
            }

            // Guardar el corte
            cortesService.registrarCorte(corte);

            model.addAttribute("mensaje-cortereg", "Corte programado registrado correctamente");
            return "redirect:/cortes-admin";  
        } else {
            model.addAttribute("error-regisCorte", "No hay administrador autenticado");
            return "redirect:/login-admin";  
        }
    }

    @GetMapping("/cortes")
    public String mostrarCortes(Model model) {
        List<CortesModel> cortes = cortesService.obtenerTodosLosCortes();
        model.addAttribute("cortes", cortes);
        return "cortes_fallas";
    }

    @GetMapping("/cortes-admin")
    public String mostrarCortesAlternativa(Model model) {
        List<CortesModel> cortes = cortesService.obtenerTodosLosCortes();
        model.addAttribute("cortes", cortes);
        return "reportes_admin"; 
    }
}
