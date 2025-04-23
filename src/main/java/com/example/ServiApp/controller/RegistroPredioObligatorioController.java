package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.PredioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegistroPredioObligatorioController {

    @Autowired
    private PredioService predioService;

    @GetMapping("/registrar-predio-obligatorio")
    public String mostrarFormularioRegistroPredioObligatorio(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        // Verificar si el usuario ya tiene un predio registrado
        boolean tienePredio = predioService.existePredioParaUsuario(usuarioLogueado.getId());
        
        if (tienePredio) {
            return "redirect:/interfaz_inicio";
        }
        
        model.addAttribute("tiposPredio", PredioModel.TipoPredio.values());
        model.addAttribute("usuario", usuarioLogueado);
        return "registrar_predio_obligatorio";
    }

    @PostMapping("/registrar-predio-obligatorio")
    public String procesarRegistroPredioObligatorio(
            @RequestParam String direccion,
            @RequestParam String barrio,
            @RequestParam int estrato,
            @RequestParam PredioModel.TipoPredio tipoPredio,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        // Crear y guardar el nuevo predio
        PredioModel nuevoPredio = new PredioModel();
        nuevoPredio.setDireccion(direccion);
        nuevoPredio.setBarrio(barrio);
        nuevoPredio.setEstrato(estrato);
        nuevoPredio.setTipoPredio(tipoPredio);
        nuevoPredio.setUsuario(usuarioLogueado);
        
        predioService.registrarPredio(nuevoPredio);
        
        redirectAttributes.addFlashAttribute("mensaje", "Predio registrado exitosamente");
        return "redirect:/interfaz_inicio";
    }
}