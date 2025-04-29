package com.example.ServiApp.config;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {
    private OAuth2User oauth2User;
    
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