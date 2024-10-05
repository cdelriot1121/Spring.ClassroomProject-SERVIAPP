package com.example.ServiApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MapPagesController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "iniciosesion";
    }

    @GetMapping("/registro")
    public String registro(){
        return "registro_usuario";
    }

    //Paginas funciones

    @GetMapping("/inicio")
    public String inicio(){
        return "interfaz_inicio";
    }

    @GetMapping("/registrar-servicio")
    public String registrarServicio(){
        return "reg_servicio";
    }

    @GetMapping("/gestionar-servicio")
    public String gestionarServicio(){
        return "gestionar_serv";
    }

    @GetMapping("/consejos-ahorro")
    public String consejosAhorro(){
        return "consejos_ahorro";
    }

    @GetMapping("/lineas-atencion")
    public String lineasAtencion(){
        return "Lineas_atencion";
    }

    @GetMapping("/perfil")
    public String perfil(){
        return "perfil_datos";
    }

}
