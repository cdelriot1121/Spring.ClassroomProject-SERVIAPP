package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.ServiApp.model.AdminModel;
import com.example.ServiApp.model.CortesModel;
import com.example.ServiApp.services.CortesService;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CortesController {

    @Autowired
    private CortesService cortesService;

    @PostMapping("/registrar-corte")
    public String registrarCorte(@ModelAttribute CortesModel corte,
                                  Model model,
                                  HttpSession sessionadmin) {

        // Obtener el administrador logueado
        AdminModel adminLogueado = (AdminModel) sessionadmin.getAttribute("adminLogueado");

        if (adminLogueado != null) {
            corte.setAdministrador(adminLogueado); 

            // Obtener los barrios seleccionados como una lista de cadenas
            List<String> barriosSeleccionados = (List<String>) sessionadmin.getAttribute("barriosSeleccionados");
            if (barriosSeleccionados != null) {
                corte.setBarrios(barriosSeleccionados);
            }

            // Guardar el corte
            cortesService.registrarCorte(corte);

            model.addAttribute("mensaje-cortereg", "Corte programado registrado correctamente");
            return "redirect:/reportes-admin";  
        } else {
            model.addAttribute("error-regisCorte", "No hay administrador autenticado");
            return "redirect:/login-admin";  
        }
    }
}
