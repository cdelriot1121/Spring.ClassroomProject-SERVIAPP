package com.example.ServiApp.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Manejador personalizado para los fallos de autenticación.
 * Redirige a páginas específicas según el tipo de error.
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        System.out.println("Error de autenticación detallado: " + exception.getClass().getName());
        System.out.println("Mensaje: " + exception.getMessage());
        if (exception.getCause() != null) {
            System.out.println("Causa: " + exception.getCause().getMessage());
        }
        
        // Manejar usuario deshabilitado - verificar por tipo o mensaje
        if (exception instanceof DisabledException || 
            (exception.getMessage() != null && exception.getMessage().contains("deshabilitado"))) {
            System.out.println("Redirigiendo a página de usuario deshabilitado");
            response.sendRedirect("/error/usuario-inhabilitado");
            return; // Asegurar que el código termine aquí
        } 
        
        // Para otros errores
        System.out.println("Redirigiendo a login con error genérico");
        response.sendRedirect("/login?error=true");
    }
}