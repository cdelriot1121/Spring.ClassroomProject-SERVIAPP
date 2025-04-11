package com.example.ServiApp.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(email);
        
        if (!usuarioOptional.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
        }
        
        UsuarioModel usuario = usuarioOptional.get();
        
        // Log para depuración
        System.out.println("Usuario encontrado en CustomUserDetailsService: " + usuario.getEmail());
        System.out.println("Rol del usuario: " + usuario.getRol().name());
        
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol().name()));
        
        return new User(
            usuario.getEmail(),
            usuario.getPassword(), 
            true, // enabled
            true, // accountNonExpired
            true, // credentialsNonExpired
            true, // accountNonLocked
            authorities
        );
    }
}
