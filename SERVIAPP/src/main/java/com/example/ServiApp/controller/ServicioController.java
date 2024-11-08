package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.ServiciosService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServiciosService servicioService;

    @PostMapping("/registrar-servicio")
    public String registrarServicio(@ModelAttribute ServicioModel servicio, Model model, HttpSession session) {
        // recuperar al usuario logueado
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        // verificar si hay algun usuario en sesion
        if (usuarioLogueado != null) {
            // asigna el id del usuario logeado en el momento
            servicio.setUsuario(usuarioLogueado);
            servicioService.registrarservicio(servicio);

            // mesnajse de exito cuando se registra el servicio
            model.addAttribute("mensaje", "Servicio registrado exitosamente");
            return "redirect:/registrar-servicio";
        } else {
            // comprobar si hay  usuarios en sesion, y si no redirigir a la pagina de inicio de sesion, o tambien si no encutra algun usuario u ocurre algun error redirige a la pogina inicio de seion 
            model.addAttribute("error", "No hay usuario autenticado");
            return "iniciosesion";  
        }
    }


    


    

    @PostMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id){
        servicioService.eliminarServicio(id);
        return "redirect:/mis-servicios";
    }




}
