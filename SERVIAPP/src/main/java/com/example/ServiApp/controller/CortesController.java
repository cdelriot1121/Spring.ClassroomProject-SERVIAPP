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

@Controller
public class CortesController {

    @Autowired
    private CortesService cortesService;


    @PostMapping("/registrar-corte")
    public String registrarCorte (@ModelAttribute CortesModel corte,
    Model model,
    HttpSession sessionadmin){

        AdminModel adminLogueado = (AdminModel) sessionadmin.getAttribute("adminLogueado");


        if (adminLogueado != null){
            corte.setAdministrador(adminLogueado);
            cortesService.registrarCorte(corte);

            //aviso
            model.addAttribute("mensaje-cortereg", "Corte programado registrado correctamente");
            return "redirect:/reportes-admin";
        } else {
            model.addAttribute("error-regisCorte", "No hay administrador autenticado");
            return "redirect:/login-admin";
        }

    }

}
