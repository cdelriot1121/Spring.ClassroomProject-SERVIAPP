package com.example.ServiApp.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @PostMapping("/registrar-corte")
    public String registrarCorte(@ModelAttribute CortesModel corte,
                                  Model model,
                                  HttpSession sessionadmin) {

        // Obtener el administrador logueado
        UsuarioModel adminLogueado = (UsuarioModel) sessionadmin.getAttribute("adminLogueado");

        if (adminLogueado != null && adminLogueado.esAdministrador()) {
            // Establecer la referencia al administrador
            corte.setAdministradorId(adminLogueado.getId());
            
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
        model.addAttribute("cortesModel", new CortesModel()); // Agregar modelo vacío para el formulario
        return "reportes_admin"; 
    }
}
