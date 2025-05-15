package com.example.ServiApp.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.services.PredioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Filtro que verifica si un usuario tiene un predio registrado.
 * Este filtro intercepta las peticiones HTTP para asegurar que los usuarios
 * completen el registro de su predio antes de acceder a ciertas funcionalidades.
 */
@Component
public class PredioRegistroFilter extends OncePerRequestFilter {

    @Autowired
    private PredioService predioService;
    
    // Lista de URLs que no requieren verificación de predio
    private final String[] urlsExcluidas = {
        "/registrar-predio-obligatorio", 
        "/completar-registro",
        "/logout", 
        "/login",
        "/", 
        "/css/", 
        "/js/", 
        "/img/", 
        "/estilos_inicio/", 
        "/img_local/",
        "/javascripts/",
        "/error",
        "/interfaz-admin",  // Los administradores no necesitan registrar predio
        "/admin"  // Cualquier ruta de administración
    };

    /**
     * Procesa cada petición HTTP para verificar si el usuario necesita registrar un predio.
     * El filtro redirige a los usuarios sin predio registrado a la página de registro obligatorio,
     * excepto para ciertas URLs excluidas y usuarios administradores.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();
        
        // Si la URL está excluida o no hay sesión activa, continuar
        if (estaExcluida(requestURI) || session == null) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Verificar si hay un usuario en la sesión
        UsuarioModel usuario = (UsuarioModel) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Si es administrador, permitir sin verificación de predio
        if (usuario.esAdministrador()) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Primero verificar si necesita completar registro (para OAuth2)
        if (!usuario.isRegistroCompleto()) {
            if (!requestURI.equals("/completar-registro")) {
                response.sendRedirect("/completar-registro");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        
        // Verificar si el usuario ya tiene un predio registrado
        boolean tienePredio = predioService.existePredioParaUsuario(usuario.getId());
        
        // Si el usuario está logueado pero no tiene predio y no está intentando registrar uno,
        // redirigir al formulario de registro obligatorio
        if (!tienePredio && !requestURI.equals("/registrar-predio-obligatorio")) {
            response.sendRedirect("/registrar-predio-obligatorio");
        } else {
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Verifica si una URI está en la lista de exclusiones.
     * Las URLs excluidas no requieren verificación de registro de predio.
     */
    private boolean estaExcluida(String uri) {
        for (String urlExcluida : urlsExcluidas) {
            if (uri.startsWith(urlExcluida)) {
                return true;
            }
        }
        return false;
    }
}