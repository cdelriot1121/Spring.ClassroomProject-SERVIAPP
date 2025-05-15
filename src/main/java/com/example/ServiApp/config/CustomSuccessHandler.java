package com.example.ServiApp.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;
import com.example.ServiApp.services.PredioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Manejador de éxito para autenticación tradicional.
 * Gestiona el flujo post-autenticación para:
 * - Usuarios normales: verifica registro completo y existencia de predio
 * - Administradores: redirige a interfaz administrativa
 * - Manejo de sesiones y atributos del usuario
 */
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final PredioService predioService;

    public CustomSuccessHandler(UsuarioRepository usuarioRepository, PredioService predioService) {
        this.usuarioRepository = usuarioRepository;
        this.predioService = predioService;
    }

    /**
     * Procesa la autenticación exitosa y determina la redirección apropiada.
     * - Para administradores: redirige a panel administrativo
     * - Para usuarios normales: verifica requisitos pendientes y redirige según corresponda
     * - Gestiona la sesión del usuario y almacena los atributos necesarios
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        String email = "";
        
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            email = userDetails.getUsername();
        } else {
            email = authentication.getName();
        }

        // Verificar si es un administrador
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

        if (isAdmin) {
            // Si es admin, buscar en la BD y añadirlo a la sesión
            Optional<UsuarioModel> adminOpt = usuarioRepository.findByEmail(email);
            if (adminOpt.isPresent()) {
                HttpSession session = request.getSession();
                session.setAttribute("adminLogueado", adminOpt.get());
                response.sendRedirect("/interfaz-admin");
            } else {
                response.sendRedirect("/login?error=usuarioNoEncontrado");
            }
        } else {
            // Si es usuario normal, buscar en la BD y añadirlo a la sesión
            Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(email);
            if (usuarioOpt.isPresent()) {
                UsuarioModel usuario = usuarioOpt.get();
                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogueado", usuario);
                
                // Verificar si el usuario está habilitado
                if (usuario.getEstado() == UsuarioModel.EstadoUsuario.DESHABILITADO) {
                    response.sendRedirect("/error/usuario-inhabilitado");
                    return;
                }
                
                // Verificar si necesita completar el registro (OAuth2)
                if (!usuario.isRegistroCompleto()) {
                    response.sendRedirect("/completar-registro");
                    return;
                }
                
                // Verificar si tiene predio registrado
                boolean tienePredio = predioService.existePredioParaUsuario(usuario.getId());
                if (!tienePredio) {
                    response.sendRedirect("/registrar-predio-obligatorio");
                } else {
                    response.sendRedirect("/interfaz_inicio");
                }
            } else {
                response.sendRedirect("/login?error=usuarioNoEncontrado");
            }
        }
    }
}