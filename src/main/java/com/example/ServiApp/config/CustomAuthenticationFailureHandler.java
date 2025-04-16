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
        
        System.out.println("Error de autenticación: " + exception.getMessage());
        
        // Si el error es porque la cuenta está deshabilitada
        if (exception instanceof DisabledException) {
            // Redirigir a la página personalizada de usuario deshabilitado
            response.sendRedirect("/error/usuario-inhabilitado");
        } else {
            // Para otros errores, usar la redirección estándar
            response.sendRedirect("/login?error=true");
        }
    }
}