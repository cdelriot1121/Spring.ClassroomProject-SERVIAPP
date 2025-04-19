package com.example.ServiApp.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Manejador personalizado para el éxito de autenticación OAuth2 con Google
 */
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;

    public CustomOAuth2SuccessHandler(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {
        
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            
            // Verificar si el usuario está habilitado
            if (!usuario.estaHabilitado()) {
                response.sendRedirect("/error/usuario-inhabilitado");
                return;
            }

            // Guardar el usuario en la sesión
            request.getSession().setAttribute("usuarioLogueado", usuario);
            
            // Verificar si necesita completar su registro
            if (!usuario.isRegistroCompleto()) {
                response.sendRedirect("/completar-registro");
                return;
            }
            
            // Redirigir a la interfaz normal
            response.sendRedirect("/interfaz_inicio");
        } else {
            response.sendRedirect("/login?error=oauth2_user_not_found");
        }
    }
}