package com.example.ServiApp.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;



// La serialización es el proceso de convertir un objeto en una secuencia de bytes,
// lo que permite que dicho objeto sea almacenado (por ejemplo, en Redis) o transmitido (por ejemplo, a través de la red).
// En Java, un objeto debe implementar la interfaz java.io.Serializable para que pueda ser serializado.
// Cuando usamos Redis como almacén de sesiones (como con Spring Session),
// los objetos que se guardan en la sesión —como usuarios autenticados— deben ser serializables.
// Si intentamos guardar un objeto no serializable, se lanzará una excepción como NotSerializableException.
// Al registrar un serializador (como RedisSerializer.java()), le decimos a Spring cómo convertir los objetos en bytes
// para almacenarlos en Redis y luego reconstruirlos cuando sea necesario.
// En este caso, estamos utilizando RedisSerializer.java() para serializar objetos Java de forma predeterminada.

/**
 * Implementación personalizada de OAuth2User que permite la serialización.
 * Necesario para almacenar la información del usuario OAuth2 en la sesión Redis.
 * Gestiona los atributos y autoridades del usuario autenticado por OAuth2.
 */
public class CustomOAuth2User implements OAuth2User, Serializable {
    private OAuth2User oauth2User;
    private static final long serialVersionUID = 1L;

    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Asignar el rol de usuario (ROLE_USUARIO) por defecto para usuarios OAuth2
        return AuthorityUtils.createAuthorityList("ROLE_USUARIO");
    }

    @Override
    public String getName() {
        return oauth2User.getAttribute("name");
    }

    public String getEmail() {
        return oauth2User.getAttribute("email");
    }
}