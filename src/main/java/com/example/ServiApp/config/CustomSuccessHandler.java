package com.example.ServiApp.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CustomSuccessHandler(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

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

        // Log the authenticated user and roles
        System.out.println("Usuario autenticado: " + authentication.getName());
        System.out.println("Roles: " + authentication.getAuthorities());

        // Obtener el usuario autenticado y guardarlo en la sesión
        String email = authentication.getName();
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            request.getSession().setAttribute(
                usuario.esAdministrador() ? "adminLogueado" : "usuarioLogueado", 
                usuario
            );
            System.out.println("Usuario almacenado en sesión: " + usuario.getNombre() + " con rol: " + usuario.getRol());
        } else {
            System.out.println("ERROR: No se encontró el usuario con email: " + email);
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
        System.out.println("Redirigiendo a: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}