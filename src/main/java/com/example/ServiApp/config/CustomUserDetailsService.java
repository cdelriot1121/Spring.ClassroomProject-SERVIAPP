package com.example.ServiApp.config;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ServiApp.model.UsuarioModel;
import com.example.ServiApp.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Normalizar el email (eliminar espacios y convertir a minúsculas)
        String emailNormalizado = email.trim().toLowerCase();

        // 2. Buscar usuario por email
        Optional<UsuarioModel> usuarioOptional = usuarioRepository.findByEmail(emailNormalizado);

        UsuarioModel usuario = usuarioOptional.orElseThrow(
                () -> new UsernameNotFoundException("No se encontró usuario con email: " + emailNormalizado));

        // 3. Registrar detalles para depuración (opcional)
        System.out.println("Usuario encontrado: " + usuario.getEmail() +
                " | Rol: " + usuario.getRol() +
                " | Contraseña (hash): " + usuario.getPassword().substring(0, 60) + "...");

        // 4. Crear autoridades (roles)
        List<GrantedAuthority> autoridades = Collections.singletonList(
                new SimpleGrantedAuthority(usuario.getRol().name()));

        // 5. Retornar UserDetails con las credenciales
        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                true, // cuenta habilitada
                true, // cuenta no expirada
                true, // credenciales no expiradas
                true, // cuenta no bloqueada
                autoridades);
    }


    
}