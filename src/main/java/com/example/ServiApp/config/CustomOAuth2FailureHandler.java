package com.example.ServiApp.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Manejador personalizado para los fallos de autenticación OAuth2.
 */
public class CustomOAuth2FailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        System.out.println("Error OAuth2: " + exception.getMessage());
        
        // Verificar el mensaje de error
        if (exception.getMessage() != null && 
            (exception.getMessage().contains("deshabilitado") || 
             exception.getMessage().contains("usuario_deshabilitado"))) {
            // Redirigir a la página específica
            response.sendRedirect("/error/usuario-inhabilitado");
        } else {
            // Para otros errores de OAuth2
            response.sendRedirect("/login?error=oauth2_error");
        }
    }
}