package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.PredioService;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CompletarRegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/completar-registro")
    public String mostrarFormularioCompletarRegistro(Model model, HttpSession session) {
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        if (usuario.isRegistroCompleto()) {
            return "redirect:/interfaz_inicio";
        }
        
        model.addAttribute("usuario", usuario);
        return "completar_registro";
    }

    @Autowired
    private PredioService predioService;

    @PostMapping("/completar-registro")
    public String procesarCompletarRegistro(
            @RequestParam(required = false) String nombre,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            HttpSession session, 
            Model model) {
        
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuarioLogueado");
        
        if (usuario == null) {
            return "redirect:/login";
        }
        
        // Validar que las contraseñas coincidan
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            model.addAttribute("usuario", usuario);
            return "completar_registro";
        }
        
        // Actualizar datos del usuario
        if (nombre != null && !nombre.trim().isEmpty()) {
            usuario.setNombre(nombre);
        }
        
        // Actualizar contraseña y marcar como registro completo
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setRegistroCompleto(true);
        
        // Guardar cambios
        usuarioService.guardarUsuario(usuario);
        
        // Actualizar la sesión
        session.setAttribute("usuarioLogueado", usuario);
        
        // Verificar si el usuario tiene un predio registrado
        boolean tienePredio = predioService.existePredioParaUsuario(usuario.getId());
        
        if (!tienePredio) {
            // Si no tiene predio, redirigir al formulario de registro obligatorio
            return "redirect:/registrar-predio-obligatorio";
        }
        
        // Si ya tiene predio (caso poco probable para un usuario nuevo), 
        // continuar a la interfaz de inicio
        return "redirect:/interfaz_inicio?bienvenido=true";
    }
}