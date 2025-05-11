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
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/predios")
public class PredioController {

    @Autowired
    private UsuarioService usuarioService;

    // Muestra el formulario para registrar un predio
    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        // Verificar si el usuario ya tiene un predio registrado
        Optional<PredioModel> predioExistente = usuarioService.obtenerPredioPorUsuario(usuarioLogueado.getId());
        
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
        Optional<PredioModel> predioExistente = usuarioService.obtenerPredioPorUsuario(usuarioLogueado.getId());
        
        if (predioExistente.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ya tiene un predio registrado. Puede editarlo desde su perfil.");
            return "redirect:/mi-predio";
        }
        
        // Crear y guardar el nuevo predio
        PredioModel nuevoPredio = new PredioModel();
        nuevoPredio.setDireccion(direccion);
        nuevoPredio.setBarrio(barrio);
        nuevoPredio.setEstrato(estrato);
        nuevoPredio.setTipoPredio(tipoPredio);
        
        usuarioService.registrarPredioPara(usuarioLogueado.getId(), nuevoPredio);
        
        // Actualizar la sesión con el usuario actualizado
        Optional<UsuarioModel> usuarioActualizadoOpt = usuarioService.obtenerUsuarioPorId(usuarioLogueado.getId());
        if (usuarioActualizadoOpt.isPresent()) {
            session.setAttribute("usuarioLogueado", usuarioActualizadoOpt.get());
        }
        
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
        
        Optional<PredioModel> predio = usuarioService.obtenerPredioPorUsuario(usuarioLogueado.getId());
        
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
        
        Optional<PredioModel> predioExistente = usuarioService.obtenerPredioPorUsuario(usuarioLogueado.getId());
        
        if (predioExistente.isPresent()) {
            PredioModel predioActual = predioExistente.get();
            // Actualizar los campos
            predioActual.setDireccion(direccion);
            predioActual.setBarrio(barrio);
            predioActual.setEstrato(estrato);
            predioActual.setTipoPredio(tipoPredio);
            
            usuarioService.actualizarPredio(usuarioLogueado.getId(), predioActual);
            
            // Actualizar la sesión con el usuario actualizado
            Optional<UsuarioModel> usuarioActualizadoOpt = usuarioService.obtenerUsuarioPorId(usuarioLogueado.getId());
            if (usuarioActualizadoOpt.isPresent()) {
                session.setAttribute("usuarioLogueado", usuarioActualizadoOpt.get());
            }
            
            redirectAttributes.addFlashAttribute("mensaje", "Predio actualizado exitosamente");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se encontró un predio asociado a su cuenta");
        }
        
        return "redirect:/mi-predio";
    }

    // Ya no se permite eliminar predios ya que son obligatorios
    // Se comenta este método o se elimina
    /*
    @PostMapping("/eliminar")
    public String eliminarPredio(HttpSession session, RedirectAttributes redirectAttributes) {
        // Código comentado ya que los predios ahora son obligatorios
        return "redirect:/mi-predio";
    }
    */
}