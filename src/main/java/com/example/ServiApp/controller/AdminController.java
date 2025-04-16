package com.example.ServiApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ServiApp.model.Falla_Ser_Model;
import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.FallasUserService;
import com.example.ServiApp.services.ServiciosService;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

/**
 * Controlador para la gestión de administradores.
 * Maneja autenticación y operaciones específicas de administración.
 */
@Controller
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Autentica administradores usando el modelo unificado de usuarios.
     * Ya no requiere PIN después de la migración.
     */
    @PostMapping("/login-admin")
    public String login(@RequestParam("usuario-admin") String email,
            @RequestParam("password") String password,
            Model model, HttpSession sessionadmin) {
        UsuarioModel admin = usuarioService.autenticarAdministrador(email, password);
        if (admin != null) {
            sessionadmin.setAttribute("adminLogueado", admin);
            return "redirect:/interfaz-admin";
        } else {
            model.addAttribute("error", "Los datos son incorrectos");
            return "login-admin";
        }
    }

    @GetMapping("/registro-admin")
    public String mostrarRegistroAdmin(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        return "registro-admin";
    }

    /**
     * Registra un nuevo administrador asignando el rol ADMINISTRADOR.
     * Parte de la migración para unificar modelos Usuario y Administrador.
     */
    @PostMapping("/registrar-admin")
    public String registrarAdmin(@ModelAttribute("usuario") UsuarioModel usuario, Model model) {
        usuario.setRol(UsuarioModel.Rol.ROLE_ADMINISTRADOR);
        UsuarioModel administradorRegistrado = usuarioService.registrarUsuario(usuario);

        if (administradorRegistrado != null) {
            return "redirect:/login-admin?exito=true";
        } else {
            model.addAttribute("error", "No se pudo registrar el administrador");
            return "registro-admin";
        }
    }

    @GetMapping("/interfaz-admin")
    public String listarUsuarios(Model model) {
        List<UsuarioModel> usuarios = usuarioService.obtenerUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "interfaz-admin";
    }

    

    @Autowired
    private ServiciosService serviciosService;

    @GetMapping("/gestionar-servicios-admin")
    public String listarServicios(Model model) {
        List<ServicioModel> servicios = serviciosService.ObtenerServicios();
        model.addAttribute("servicios", servicios);
        return "ges-servicios-admin";
    }

    @PostMapping("/eliminar-servicio/{id}")
    public String eliminarServicio(@PathVariable("id") Long id) {
        serviciosService.eliminarServicio(id);
        return "redirect:/gestionar-servicios-admin";
    }

    @Autowired
    private FallasUserService fallasUserService;

    @GetMapping("/reportes_usuarios")
    public String listarFallasUser(Model model) {
        List<Falla_Ser_Model> fallas = fallasUserService.obtenerFallas();
        model.addAttribute("fallas", fallas);
        return "ReportesUserAdmin";
    }



    @PostMapping("/inhabilitar-usuario/{id}")
    public String inhabilitarUsuario(@PathVariable("id") Long id) {
        usuarioService.inhabilitarUsuario(id);
        return "redirect:/interfaz-admin";
    }
    
    @PostMapping("/habilitar-usuario/{id}")
    public String habilitarUsuario(@PathVariable("id") Long id) {
        usuarioService.habilitarUsuario(id);
        return "redirect:/interfaz-admin";
    }
    



    
    
    








}
