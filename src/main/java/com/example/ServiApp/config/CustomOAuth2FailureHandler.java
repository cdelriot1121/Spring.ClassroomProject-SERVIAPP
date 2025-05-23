package com.example.ServiApp.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Manejador de errores para la autenticación OAuth2.
 * Gestiona diferentes tipos de fallos durante el proceso de autenticación OAuth2:
 * - Usuarios deshabilitados
 * - Errores de proveedor OAuth2
 * - Redirecciones específicas según el tipo de error
 */
public class CustomOAuth2FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        // Si es un error de usuario deshabilitado, redirigir a la página correspondiente
        if (exception instanceof DisabledException || 
            exception.getMessage().contains("deshabilitado") || 
            exception.getMessage().equals("usuario_deshabilitado")) {
            
            System.out.println("OAuth2: Redirigiendo a página de usuario deshabilitado");
            response.sendRedirect("/error/usuario-inhabilitado");
            return;
        }
        
        // Para otros errores de OAuth2
        response.sendRedirect("/login?error=oauth2_error");
    }
}