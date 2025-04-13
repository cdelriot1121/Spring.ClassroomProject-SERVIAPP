package com.example.ServiApp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException {

        // 1. Validar que la autenticación no sea nula
        if (authentication == null || authentication.getAuthorities() == null) {
            response.sendRedirect("/login?error=auth_null");
            return;
        }

        // 2. Buscar el rol y determinar la URL de redirección
        String redirectUrl = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(role -> role.equals("ROLE_USUARIO") || role.equals("ROLE_ADMINISTRADOR"))
                .findFirst()
                .map(role -> role.equals("ROLE_ADMINISTRADOR")
                        ? "/interfaz-admin"
                        : "/interfaz-inicio")
                .orElse("/login?error=invalid_role");

        // 3. Redirigir
        response.sendRedirect(redirectUrl);
    }
}