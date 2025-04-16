package com.example.ServiApp.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
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
        
        System.out.println("Error OAuth2 detallado: " + exception.getClass().getName());
        System.out.println("Mensaje OAuth2: " + exception.getMessage());
        
        // Verificar todas las posibles condiciones de usuario deshabilitado
        if (exception instanceof DisabledException || 
            (exception.getMessage() != null && 
             (exception.getMessage().contains("deshabilitado") || 
              exception.getMessage().contains("usuario_deshabilitado") ||
              exception.getMessage().equals("usuario_deshabilitado")))) {
            
            System.out.println("OAuth2: Redirigiendo a página de usuario deshabilitado");
            response.sendRedirect("/error/usuario-inhabilitado");
            return; // Asegurar que el código termine aquí
        }
        
        // Verificar también la causa raíz
        Throwable cause = exception.getCause();
        if (cause != null) {
            System.out.println("Causa OAuth2: " + cause.getMessage());
            if (cause instanceof DisabledException || 
                (cause.getMessage() != null && cause.getMessage().contains("deshabilitado"))) {
                System.out.println("OAuth2 (causa): Redirigiendo a página de usuario deshabilitado");
                response.sendRedirect("/error/usuario-inhabilitado");
                return; // Asegurar que el código termine aquí
            }
        }
        
        // Para cualquier otro error de OAuth2
        System.out.println("Redirigiendo a login con error OAuth2");
        response.sendRedirect("/login?error=oauth2_error");
    }
}