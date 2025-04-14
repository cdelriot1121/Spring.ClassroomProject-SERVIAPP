package com.example.ServiApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapPagesController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "iniciosesion";
    }

    @GetMapping("/registro")
    public String registro() {
        return "registro_usuario";
    }

    @GetMapping("/interfaz-inicio")
    public String interfazInicio() {
        return "interfaz_inicio";
    }

    @GetMapping("/registrar-servicio")
    public String registrarServicio() {
        return "reg_servicio";
    }

    @GetMapping("/consejos-ahorro")
    public String consejosAhorro() {
        return "consejos_ahorro";
    }

    @GetMapping("/lineas-atencion")
    public String lineasAtencion() {
        return "Lineas_atencion";
    }

    @GetMapping("/acercade")
    public String acercade() {
        return "/acerca_del_proyecto";
    }

    @GetMapping("/login-admin")
    public String loginAdmin() {
        return "login-admin";
    }

    @GetMapping("/reportes-admin")
    public String reportesAdmin() {
        return "reportes_admin";
    }

    @GetMapping("/consejos-admin")
    public String consejosAdmin() {
        return "add_consejos_admin";
    }
}
