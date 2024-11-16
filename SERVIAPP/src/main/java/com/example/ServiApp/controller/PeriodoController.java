package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ServiApp.model.PeriodoModel;
import com.example.ServiApp.services.PeriodoService;
import com.example.ServiApp.services.ServiciosService;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PeriodoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ServiciosService servicioService;

    @Autowired
    private PeriodoService periodoService;
    


    @PostMapping("/calcular-consumo")
    public String calcularConsumo(@ModelAttribute PeriodoModel periodo, Model model, HttpSession session){


        return "gestionar_serv";
    }




}
