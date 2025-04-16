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
import org.springframework.security.authentication.DisabledException;

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

        // 3. Verificar si el usuario está habilitado
        if (!usuario.estaHabilitado()) {
            throw new DisabledException("Usuario deshabilitado");
        }

        // 4. Registrar detalles para depuración (opcional)
        System.out.println("Usuario encontrado: " + usuario.getEmail() +
                " | Rol: " + usuario.getRol() +
                " | Estado: " + usuario.getEstado() +
                " | Contraseña (hash): " + usuario.getPassword().substring(0, 10) + "...");

        // 5. Crear autoridades (roles)
        List<GrantedAuthority> autoridades = Collections.singletonList(
                new SimpleGrantedAuthority(usuario.getRol().name()));
        
        // 6. Retornar UserDetails
        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                true,        // cuenta habilitada (ya verificamos esto antes)
                true,        // cuenta no expirada
                true,        // credenciales no expiradas
                true,        // cuenta no bloqueada
                autoridades);
    }

    
}