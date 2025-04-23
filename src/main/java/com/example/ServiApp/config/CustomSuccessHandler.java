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
import com.example.ServiApp.services.PredioService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Manejador personalizado para el éxito de autenticación en la aplicación.
 * 
 * Esta clase se encarga de realizar acciones específicas cuando un usuario
 * se autentica correctamente, como guardar el usuario en la sesión y
 * redirigir a la página correspondiente según el rol del usuario.
 */
@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    // Repositorio para acceder a los datos de usuario en la base de datos
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private PredioService predioService;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param usuarioRepository Repositorio para acceder a datos de usuario
     */
    @Autowired
    public CustomSuccessHandler(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Método que se ejecuta cuando un usuario se autentica correctamente.
     * 
     * Se encarga de:
     * 1. Validar la autenticación
     * 2. Obtener el usuario de la base de datos
     * 3. Guardar el usuario en la sesión según su rol
     * 4. Determinar la URL de redirección según el rol
     * 5. Redirigir al usuario a la página correspondiente
     * 
     * @param request La solicitud HTTP actual
     * @param response La respuesta HTTP que se enviará
     * @param authentication Objeto que contiene la información de autenticación
     * @throws IOException Si ocurre un error durante la redirección
     */
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

        // Registrar información de depuración en logs
        System.out.println("Usuario autenticado: " + authentication.getName());
        System.out.println("Roles: " + authentication.getAuthorities());

        // 2. Obtener el usuario autenticado de la base de datos
        String email = authentication.getName();
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(email);
        
        // 3. Guardar el usuario en la sesión según su rol
        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            
            // Guardar en la sesión con la clave adecuada según el rol
            // "adminLogueado" para administradores, "usuarioLogueado" para usuarios normales
            request.getSession().setAttribute(
                usuario.esAdministrador() ? "adminLogueado" : "usuarioLogueado", 
                usuario
            );
            
            // Si es un usuario normal (no administrador)
            if (!usuario.esAdministrador()) {
                // Verificar si necesita completar registro (para OAuth2)
                if (!usuario.isRegistroCompleto()) {
                    response.sendRedirect("/completar-registro");
                    return;
                }
                
                // Verificar si tiene predio registrado
                boolean tienePredio = predioService.existePredioParaUsuario(usuario.getId());
                if (!tienePredio) {
                    response.sendRedirect("/registrar-predio-obligatorio");
                    return;
                }
            }
            
            System.out.println("Usuario almacenado en sesión: " + usuario.getNombre() + " con rol: " + usuario.getRol());
        } else {
            System.out.println("ERROR: No se encontró el usuario con email: " + email);
        }

        // 4. Determinar la URL de redirección según el rol
        String redirectUrl = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(role -> role.equals("ROLE_USUARIO") || role.equals("ROLE_ADMINISTRADOR"))
                .findFirst()
                .map(role -> role.equals("ROLE_ADMINISTRADOR")
                        ? "/interfaz-admin"   // Redirección para administradores
                        : "/interfaz_inicio") // Redirección para usuarios normales
                .orElse("/login?error=invalid_role"); // Redirección en caso de rol inválido

        // 5. Redirigir al usuario a la página correspondiente
        System.out.println("Redirigiendo a: " + redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}