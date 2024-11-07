package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuarios/registrar")
    public String registrarUsuario(@ModelAttribute UsuarioModel usuario, Model model) {
        UsuarioModel usuarioRegistrado = usuarioService.registrarUsuario(usuario);
        if (usuarioRegistrado != null) {
            return "redirect:/registro?exito=true&nombre=" + usuario.getNombre();
        } else {
            model.addAttribute("error", "No se pudo registrar el usuario");
            return "registro";
        }
    }



   @GetMapping("/usuarios/contador")
public ResponseEntity<Long> obtenerContadorUsuarios() {
    return ResponseEntity.ok(usuarioService.contarUsuarios());
}


    @PostMapping("/login-usuario")
    public String login_usuario(@RequestParam("correo") String email,
                                @RequestParam("contraseña") String contraseña,
                                Model model,
                                HttpSession session) {
        UsuarioModel usuario = usuarioService.autenticar(email, contraseña);
        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/interfaz_inicio";
        } else {
            model.addAttribute("error", "Los datos ingresados son incorrectos");
            return "iniciosesion";
        }
    }

    @GetMapping("/interfaz_inicio")
    public String mostrarInterfazInicio(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado != null) {
            model.addAttribute("nombreUsuario", usuarioLogueado.getNombre());
            System.out.println("Nombre del usuario logueado: " + usuarioLogueado.getNombre());
        }
        return "interfaz_inicio"; 
    }

    @GetMapping("/datos-personales")
    public String mostrarDatosPersonales(Model model, HttpSession session) {
        UsuarioModel usuarioLogueado = (UsuarioModel) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado != null) {
          
            model.addAttribute("usuario", usuarioLogueado);
        } else {
            return "redirect:/login";
        }
        return "perfil_datos";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioEdicion (@PathVariable Long id, Model model){
        UsuarioModel usuario = usuarioService.obtenerUsuarioPorId(id).orElseThrow();

        model.addAttribute("usuario",usuario);
        model.addAttribute("editarUsuarioId",id);
        return "perfil_datos";
    }
    
    @PostMapping("/usuarios/actualizar/{id}")
    public String actualizarUsuario (@PathVariable Long id, @ModelAttribute UsuarioModel usuario, Model model){

        UsuarioModel usuarioExistente = usuarioService.obtenerUsuarioPorId(id) 
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + id));

        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setEstrato(usuario.getEstrato());

        usuarioService.guardarUsuario(usuarioExistente);

         return "redirect:/datos-personales";
    }

}
