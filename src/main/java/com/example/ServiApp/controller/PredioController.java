package com.example.ServiApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String registrarPredio(
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
        
        // Verificar si el usuario ya tiene un predio registrado
        Optional<PredioModel> predioExistente = predioService.obtenerPredioPorUsuario(usuarioLogueado);
        
        if (predioExistente.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ya tiene un predio registrado. Puede editarlo o eliminarlo para registrar uno nuevo.");
            return "redirect:/mi-predio";
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
        return "redirect:/mi-predio";
    }

    // Muestra el formulario para editar un predio
    @GetMapping("/editar")
    public String mostrarFormularioEdicion(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        Optional<PredioModel> predio = predioService.obtenerPredioPorUsuario(usuarioLogueado);
        
        if (predio.isPresent()) {
            model.addAttribute("predio", predio.get());
            model.addAttribute("tiposPredio", PredioModel.TipoPredio.values());
            model.addAttribute("editarPredio", true);
            model.addAttribute("section", "mi-predio");
            return "perfil_datos";
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró un predio asociado a su cuenta");
            return "redirect:/mi-predio";
        }
    }

    // Procesa la actualización de un predio
    @PostMapping("/actualizar")
    public String actualizarPredio(
            @RequestParam Long id,
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
        
        // Verificar que el predio pertenezca al usuario actual
        Optional<PredioModel> predioExistente = predioService.obtenerPredioPorId(id);
        
        if (predioExistente.isPresent() && predioExistente.get().getUsuario().getId() == usuarioLogueado.getId()) {
            PredioModel predioActual = predioExistente.get();
            
            // Actualizar los campos
            predioActual.setDireccion(direccion);
            predioActual.setBarrio(barrio);
            predioActual.setEstrato(estrato);
            predioActual.setTipoPredio(tipoPredio);
            
            predioService.actualizarPredio(predioActual);
            redirectAttributes.addFlashAttribute("mensaje", "Predio actualizado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró un predio asociado a su cuenta");
        }
        
        return "redirect:/mi-predio";
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
        
        return "redirect:/mi-predio";
    }
}