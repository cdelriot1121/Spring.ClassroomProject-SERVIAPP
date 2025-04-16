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

        String email = oAuth2User.getAttribute("email");
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();

            // Verificar si el usuario está habilitado
            if (!usuario.estaHabilitado()) {
                throw new OAuth2AuthenticationException("El usuario está deshabilitado.");
            }

            System.out.println("Usuario de Google existente: " + email);
        } else {
            // Crear nuevo usuario si no existe
            UsuarioModel nuevoUsuario = new UsuarioModel();
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setNombre(oAuth2User.getAttribute("name"));
            nuevoUsuario.setRol(UsuarioModel.Rol.ROLE_USUARIO);
            nuevoUsuario.setEstado(UsuarioModel.EstadoUsuario.HABILITADO);
            nuevoUsuario.setPassword(new BCryptPasswordEncoder().encode("google_" + System.currentTimeMillis()));
            usuarioRepository.save(nuevoUsuario);

            System.out.println("Nuevo usuario de Google registrado: " + email);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USUARIO")),
                oAuth2User.getAttributes(),
                "email");
    }
}
