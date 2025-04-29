package com.example.ServiApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        
        // Obtener info del usuario desde el provider (Google, Facebook, etc)
        String email = oauth2User.getAttribute("email");
        String nombre = oauth2User.getAttribute("name");
        
        // Buscar si el usuario ya existe en nuestra base de datos
        UsuarioModel usuarioExistente = usuarioRepository.findByEmail(email).orElse(null);
        
        if (usuarioExistente == null) {
            // Crear nuevo usuario con registro incompleto (necesitará establecer contraseña)
            UsuarioModel nuevoUsuario = UsuarioModel.crearUsuario(nombre, email, "");
            nuevoUsuario.setRegistroCompleto(false);
            usuarioRepository.save(nuevoUsuario);
        }
        
        return new CustomOAuth2User(oauth2User);
    }
}
