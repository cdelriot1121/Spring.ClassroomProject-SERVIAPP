package com.example.ServiApp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.ServiApp.model.PredioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

/**
 * Controlador que maneja el proceso de registro obligatorio de predios.
 * Se encarga de mostrar y procesar el formulario de registro de predios
 * para usuarios que aún no han registrado uno.
 */
@Controller
public class RegistroPredioObligatorioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Muestra el formulario de registro de predio.
     * Verifica si el usuario ya tiene un predio registrado y redirige según corresponda.
     */
    @GetMapping("/registrar-predio-obligatorio")
    public String mostrarFormularioRegistroPredioObligatorio(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }
        
        // Verificar si el usuario ya tiene un predio registrado
        boolean tienePredio = usuarioService.existePredioParaUsuario(usuarioLogueado.getId());
        
        if (tienePredio) {
            return "redirect:/interfaz_inicio";
        }
        
        model.addAttribute("tiposPredio", PredioModel.TipoPredio.values());
        model.addAttribute("usuario", usuarioLogueado);
        return "registrar_predio_obligatorio";
    }

    /**
     * Procesa el formulario de registro de predio.
     * Crea un nuevo predio para el usuario y actualiza la sesión con la información actualizada.
     */
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
        
        usuarioService.registrarPredioPara(usuarioLogueado.getId(), nuevoPredio);
        
        // Obtener el usuario actualizado desde la base de datos y actualizar la sesión
        Optional<UsuarioModel> usuarioActualizadoOpt = usuarioService.obtenerUsuarioPorId(usuarioLogueado.getId());
        if (usuarioActualizadoOpt.isPresent()) {
            // Actualizar la sesión con el usuario que incluye el predio
            session.setAttribute("usuarioLogueado", usuarioActualizadoOpt.get());
        }
        
        redirectAttributes.addFlashAttribute("mensaje", "Predio registrado exitosamente");
        return "redirect:/interfaz_inicio";
    }
}