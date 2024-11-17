package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado != null) {

            if (servicioService.existeServicioPorTipoYUsuario(servicio.getTipo_servicio(),usuarioLogueado)){
                model.addAttribute("errorDuplicado", "Ya tienes este servicio registrado");
                return "redirect:/registrar-servicio";
            }
             servicio.setUsuario(usuarioLogueado);
            servicioService.registrarservicio(servicio);

            model.addAttribute("mensaje", "Servicio registrado exitosamente");
            return "redirect:/registrar-servicio";
        } else {
            model.addAttribute("error", "No hay usuario autenticado");
            return "iniciosesion";  
        }
    }

     @GetMapping("/editar/{id}")
    public String editarServicio(@PathVariable Long id, Model model) {
        servicioService.obtenerServicioPorId(id).orElseThrow(() ->
        new IllegalArgumentException("Servicio no encontrado con id: " + id));

        model.addAttribute("editarServicioId", id);
        model.addAttribute("servicios", servicioService.ObtenerServicios());
        model.addAttribute("section", "mis-servicios");

        return "perfil_datos";
        }


    @PostMapping("/actualizar/{id}")
    public String actualizarServicio(@PathVariable Long id, @ModelAttribute ServicioModel servicio) {
        ServicioModel servicioExistente = servicioService.obtenerServicioPorId(id)
        .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado con id: " + id));

        servicioExistente.setPoliza(servicio.getPoliza());
        servicioExistente.setHabitantes(servicio.getHabitantes());
        servicioService.actualizarServicio(id, servicioExistente);

        return "redirect:/mis-servicios"; 
    }



    @PostMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id){
        servicioService.eliminarServicio(id);
        return "redirect:/mis-servicios";
    }




}
