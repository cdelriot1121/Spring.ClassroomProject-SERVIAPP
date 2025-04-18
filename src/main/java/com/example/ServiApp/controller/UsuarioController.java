package com.example.ServiApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ServiApp.model.ServicioModel;
import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.ServiciosService;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ServiciosService servicioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/usuarios/registrar")
    public String registrarUsuario(@ModelAttribute UsuarioModel usuario, Model model) {
        System.out.println("Contraseña antes de encriptar (primeros 4 caracteres): " + 
                           (usuario.getPassword().length() > 10 ? usuario.getPassword().substring(0, 4) + "..." : usuario.getPassword()));
        
        // Solo establecemos el rol, la encriptación se hará en el servicio
        usuario.setRol(UsuarioModel.Rol.ROLE_USUARIO);
        
        UsuarioModel usuarioRegistrado = usuarioService.registrarUsuario(usuario);
        
        if (usuarioRegistrado != null) {
            return "redirect:/registro?exito=true&nombre=" + usuario.getNombre();
        } else {
            model.addAttribute("error", "No se pudo registrar el usuario");
            return "registro";
        }
    }

    @GetMapping("/usuarios/verificar-email")
    public ResponseEntity<Boolean> verificarEmail(@RequestParam String email) {
        boolean existe = usuarioService.emailYaRegistrado(email);
        return ResponseEntity.ok(existe);
    }

    @GetMapping("/usuarios/contador")
    public ResponseEntity<Long> obtenerContadorUsuarios() {
        return ResponseEntity.ok(usuarioService.contarUsuarios());
    }

    @GetMapping("/interfaz_inicio")
    public String mostrarInterfazInicio(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado != null) {
            model.addAttribute("nombreUsuario", usuarioLogueado.getNombre());
        }
        return "interfaz_inicio";
    }

    @GetMapping("/datos-personales")
    public String mostrarDatosPersonales(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        model.addAttribute("section", "datos-personales");
        if (usuarioLogueado != null) {
            model.addAttribute("usuario", usuarioLogueado);
        } else {
            return "redirect:/login";
        }
        return "perfil_datos";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        UsuarioModel usuario = usuarioService.obtenerUsuarioPorId(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        model.addAttribute("editarUsuarioId", id);
        model.addAttribute("section", "datos-personales");
        return "perfil_datos";
    }

    @PostMapping("/usuarios/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute UsuarioModel usuario, Model model) {
        model.addAttribute("section", "datos-personales");
        UsuarioModel usuarioExistente = usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));

        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setEstrato(usuario.getEstrato());
        usuarioService.guardarUsuario(usuarioExistente);

        model.addAttribute("usuario", usuarioExistente);
        model.addAttribute("section", "datos-personales");
        return "perfil_datos";
    }

    @GetMapping("/cambiar-contrasena")
    public String mostrarCambiarContrasena(Model model) {
        model.addAttribute("section", "cambiar-contrasena");
        return "perfil_datos";
    }

    @PostMapping("/usuarios/cambiar-contrasena")
    public String cambiarContraseña(@RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpSession session,
            Model model) {

        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        if (!passwordEncoder.matches(currentPassword, usuarioLogueado.getPassword())) {
            model.addAttribute("error", "La contraseña actual es incorrecta");
            model.addAttribute("section", "cambiar-contrasena");
            return "perfil_datos";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden");
            model.addAttribute("section", "cambiar-contrasena");
            return "perfil_datos";
        }

        usuarioLogueado.setPassword(passwordEncoder.encode(newPassword));
        usuarioService.guardarUsuario(usuarioLogueado);

        model.addAttribute("mensaje", "¡Contraseña cambiada con éxito!");
        model.addAttribute("section", "cambiar-contrasena");
        return "perfil_datos";
    }

    @GetMapping("/mis-servicios")
    public String listarServicios(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado != null) {
            List<ServicioModel> servicios = servicioService.obtenerServiciosPorUsuario(usuarioLogueado);
            model.addAttribute("servicios", servicios);
            model.addAttribute("section", "mis-servicios");
            return "perfil_datos";
        } else {
            model.addAttribute("error", "No hay usuario autenticado");
            return "iniciosesion";
        }
    }

}
