package com.example.ServiApp.config;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

/**
 * Servicio personalizado para cargar y procesar usuarios desde Google OAuth2
 */
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // Extraer información del usuario de Google
        String email = oAuth2User.getAttribute("email");
        String nombre = oAuth2User.getAttribute("name");
        
        // Verificar si el usuario ya existe en nuestra base de datos
        Optional<UsuarioModel> usuarioExistente = usuarioRepository.findByEmail(email);
        
        if (!usuarioExistente.isPresent()) {
            // Crear nuevo usuario si no existe
            UsuarioModel nuevoUsuario = new UsuarioModel();
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setNombre(nombre);
            // Por defecto los usuarios de Google serán usuarios normales
            nuevoUsuario.setRol(UsuarioModel.Rol.ROLE_USUARIO);
            
            // Establecer una contraseña aleatoria (nunca se usará directamente)
            // ya que el usuario se autenticará siempre con Google
            String randomPassword = "google_" + System.currentTimeMillis();
            nuevoUsuario.setPassword(new BCryptPasswordEncoder().encode(randomPassword));
            
            // Guardar el nuevo usuario
            usuarioRepository.save(nuevoUsuario);
            
            System.out.println("Nuevo usuario de Google registrado: " + email);
        } else {
            System.out.println("Usuario de Google existente: " + email);
        }

        // Devolver el usuario OAuth2 con la autoridad ROLE_USUARIO
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USUARIO")),
                oAuth2User.getAttributes(),
                "email");
    }
}
