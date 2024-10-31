package com.example.ServiApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.UsuarioService;


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
}
