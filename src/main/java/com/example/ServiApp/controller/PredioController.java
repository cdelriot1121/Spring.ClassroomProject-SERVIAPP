package com.example.ServiApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.PredioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/predios")
public class PredioController {

    @Autowired
    private PredioService predioService;

    // Muestra el formulario para registrar un predio
    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        // Verificar si el usuario ya tiene un predio registrado
        Optional<PredioModel> predioExistente = predioService.obtenerPredioPorUsuario(usuarioLogueado);
        
        if (predioExistente.isPresent()) {
            model.addAttribute("predio", predioExistente.get());
            return "editar_predio"; // Vista para editar predio existente
        } else {
            model.addAttribute("predio", new PredioModel());
            model.addAttribute("tiposPredio", PredioModel.TipoPredio.values());
            return "registrar_predio"; // Vista para nuevo registro
        }
    }

    // Procesa el registro de un nuevo predio
    @PostMapping("/registrar")
    public String registrarPredio(@ModelAttribute PredioModel predio, HttpSession session, RedirectAttributes redirectAttributes) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        // Asignar el usuario al predio
        predio.setUsuario(usuarioLogueado);
        
        // Guardar el predio
        predioService.registrarPredio(predio);
        
        redirectAttributes.addFlashAttribute("mensaje", "Predio registrado exitosamente");
        return "redirect:/perfil";
    }

    // Muestra el formulario para editar un predio
    @GetMapping("/editar")
    public String mostrarFormularioEdicion(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        Optional<PredioModel> predio = predioService.obtenerPredioPorUsuario(usuarioLogueado);
        
        if (predio.isPresent()) {
            model.addAttribute("predio", predio.get());
            model.addAttribute("tiposPredio", PredioModel.TipoPredio.values());
            return "editar_predio";
        } else {
            return "redirect:/predios/registrar";
        }
    }

    // Procesa la actualización de un predio
    @PostMapping("/actualizar")
    public String actualizarPredio(@ModelAttribute PredioModel predio, HttpSession session, RedirectAttributes redirectAttributes) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        // Verificar que el predio pertenezca al usuario actual
        Optional<PredioModel> predioExistente = predioService.obtenerPredioPorUsuario(usuarioLogueado);
        
        if (predioExistente.isPresent()) {
            PredioModel predioActual = predioExistente.get();
            
            // Mantener el ID y el usuario originales
            predio.setId(predioActual.getId());
            predio.setUsuario(usuarioLogueado);
            
            predioService.actualizarPredio(predio);
            redirectAttributes.addFlashAttribute("mensaje", "Predio actualizado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró un predio asociado a su cuenta");
        }
        
        return "redirect:/perfil";
    }

    // Elimina un predio
    @PostMapping("/eliminar")
    public String eliminarPredio(HttpSession session, RedirectAttributes redirectAttributes) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        Optional<PredioModel> predio = predioService.obtenerPredioPorUsuario(usuarioLogueado);
        
        if (predio.isPresent()) {
            predioService.eliminarPredio(predio.get().getId());
            redirectAttributes.addFlashAttribute("mensaje", "Predio eliminado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró un predio asociado a su cuenta");
        }
        
        return "redirect:/perfil";
    }
}