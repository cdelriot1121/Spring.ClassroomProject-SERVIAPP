package com.example.ServiApp.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;
import com.example.ServiApp.services.PredioService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Manejador de éxito para autenticación OAuth2.
 * Se encarga de:
 * - Procesar el inicio de sesión exitoso con proveedores OAuth2
 * - Gestionar el flujo de redirección según el estado del usuario
 * - Verificar requisitos pendientes (registro completo, predio)
 */
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UsuarioRepository usuarioRepository;
    private final PredioService predioService;

    public CustomOAuth2SuccessHandler(UsuarioRepository usuarioRepository, PredioService predioService) {
        this.usuarioRepository = usuarioRepository;
        this.predioService = predioService;
    }

    /**
     * Maneja el proceso posterior a una autenticación OAuth2 exitosa.
     * Verifica el estado del usuario y redirige según corresponda:
     * - A completar registro si es necesario
     * - A registrar predio si no tiene uno
     * - A la interfaz principal si todo está completo
     */
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, 
            HttpServletResponse response, 
            Authentication authentication
    ) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        
        // Buscar el usuario en la base de datos
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);
            
            // Verificar si está habilitado
            if (usuario.getEstado() == UsuarioModel.EstadoUsuario.DESHABILITADO) {
                getRedirectStrategy().sendRedirect(request, response, "/error/usuario-inhabilitado");
                return;
            }
            
            // Verificar si necesita completar el registro (OAuth2)
            if (!usuario.isRegistroCompleto()) {
                getRedirectStrategy().sendRedirect(request, response, "/completar-registro");
                return;
            }
            
            // Verificar si tiene predio registrado
            boolean tienePredio = predioService.existePredioParaUsuario(usuario.getId());
            if (!tienePredio) {
                getRedirectStrategy().sendRedirect(request, response, "/registrar-predio-obligatorio");
            } else {
                getRedirectStrategy().sendRedirect(request, response, "/interfaz_inicio");
            }
        } else {
            // No debería ocurrir ya que el CustomOAuth2UserService crea el usuario
            getRedirectStrategy().sendRedirect(request, response, "/login?error=usuarioNoEncontrado");
        }
    }
}