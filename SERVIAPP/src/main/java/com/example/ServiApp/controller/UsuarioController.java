package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        usuarioService.registrarUsuario(usuario);
        model.addAttribute("mensaje", "Te has registrado exitosamente");
        return "redirect:/login";
    }


   @GetMapping("/usuarios/contador")
public ResponseEntity<Long> obtenerContadorUsuarios() {
    return ResponseEntity.ok(usuarioService.contarUsuarios());
}



 @PostMapping("/login-usuario")
public String login_usuario(@RequestParam("correo") String email,
                            @RequestParam("contraseña") String contraseña,
                            Model model,
                            HttpSession session) {  // parametro HttpSession 
    UsuarioModel usuario = usuarioService.autenticar(email, contraseña);
    if (usuario != null) {
        // guardar usuario en la sesion
        session.setAttribute("usuarioLogueado", usuario);
        return "interfaz_inicio";
    } else {
        model.addAttribute("error", "Los datos ingresados son incorrectos");
        return "iniciosesion";
    }
}




    
}
