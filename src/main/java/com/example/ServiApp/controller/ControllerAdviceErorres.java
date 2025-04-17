package com.example.ServiApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerAdviceErorres {

    // Manejo de excepciones globales (por ejemplo, error 500)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServerError(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error/500"; // Redirige a la plantilla error/500
    }

    // Manejo de error 404 - Página no encontrada
    @RequestMapping("/error/404")
    public String handleNotFound(Model model) {
        model.addAttribute("errorMessage", "Página no encontrada.");
        return "error/404"; // Redirige a la plantilla error/404
    }

    // Manejo de error 403 - Acceso denegado
    @RequestMapping("/error/403")
    public String handleForbidden(Model model) {
        model.addAttribute("errorMessage", "Acceso denegado.");
        return "error/403"; // Redirige a la plantilla error/403
    }

    // Manejo de error 400 - Bad Request
    @RequestMapping("/error/400")
    public String handleBadRequest(Model model) {
        model.addAttribute("errorMessage", "Solicitud incorrecta.");
        return "error/400"; // Redirige a la plantilla error/400
    }

    // Manejo de error 401 - Unauthorized
    @RequestMapping("/error/401")
    public String handleUnauthorized(Model model) {
        model.addAttribute("errorMessage", "No autorizado. Inicia sesión para continuar.");
        return "error/401"; // Redirige a la plantilla error/401
    }

    @RequestMapping("/error/usuario-inhabilitado")
    public String usuarioInhabilitado() {
        return "error/usuario-inhabilitado";
    }

}
