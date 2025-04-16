package com.example.ServiApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.ServiApp.repository.UsuarioRepository;

/**
 * Configuración principal de seguridad para la aplicación ServiApp.
 * 
 * Esta clase define todas las reglas de acceso, autenticación y autorización
 * para las diferentes rutas y recursos de la aplicación.
 * 
 * @EnableWebSecurity - Habilita la seguridad web en Spring Security
 * @EnableMethodSecurity - Permite usar anotaciones de seguridad en métodos (@PreAuthorize, etc.)
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    // Servicio para cargar datos de usuario personalizado desde la base de datos
    private final CustomUserDetailsService customUserDetailsService;
    
    // Manejador personalizado para redirecciones después de login exitoso
    private final CustomSuccessHandler customSuccessHandler;

    // Servicio personalizado para OAuth2
    private final CustomOAuth2UserService customOAuth2UserService;

    // Repositorio de usuarios
    private final UsuarioRepository usuarioRepository;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param customUserDetailsService Servicio para cargar datos de usuario
     * @param customSuccessHandler Manejador para el éxito de autenticación
     * @param customOAuth2UserService Servicio para OAuth2
     * @param usuarioRepository Repositorio de usuarios
     * 
     * @Lazy se usa para evitar dependencias circulares durante la inicialización
     */
    @Autowired
    public SecurityConfiguration(@Lazy CustomUserDetailsService customUserDetailsService,
            @Lazy CustomSuccessHandler customSuccessHandler,
            @Lazy CustomOAuth2UserService customOAuth2UserService,
            @Lazy UsuarioRepository usuarioRepository) {
        this.customUserDetailsService = customUserDetailsService;
        this.customSuccessHandler = customSuccessHandler;
        this.customOAuth2UserService = customOAuth2UserService;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Configura el filtro de seguridad con reglas específicas para la aplicación.
     * 
     * Define qué rutas son públicas y cuáles requieren autenticación
     * con roles específicos (ROLE_USUARIO, ROLE_ADMINISTRADOR).
     * 
     * @param http Configuración HttpSecurity de Spring
     * @return El SecurityFilterChain configurado
     * @throws Exception Si ocurre algún error durante la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactivar CSRF para facilitar las peticiones POST desde formularios
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // ======== ACCESO PÚBLICO ========
                        // Recursos públicos y páginas de acceso general
                        .requestMatchers("/", "/login", "/registro", "/acercade", "/usuarios/verificar-email", 
                                "/usuarios/registrar", "/usuarios/contador", "/consejos-ahorro",
                                "/error/usuario-inhabilitado").permitAll()  // Añadir la página de error
                        
                        // Recursos estáticos (CSS, JavaScript, imágenes)
                        .requestMatchers("/javascripts/**",
                                "/main.css", 
                                "/estilos_inicio/**",
                                "/estilos_interfaz-usuario/**",
                                "/img_local/**",
                                "/estilos_interfaz-admin/**",
                                "/favicon.ico").permitAll()
                        
                        // ======== ACCESO PARA ADMINISTRADORES ========
                        // Rutas específicas para administradores (GET)
                        .requestMatchers("/reportes-admin", "/consejos-admin", "/interfaz-admin", 
                                "/gestionar-servicios-admin", "/cortes-admin", "/reportes_usuarios", 
                                "/admin/**", "/login-admin", "/registro-admin").hasAuthority("ROLE_ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/registrar-corte", "/registrar-consejo", 
                                "/login-admin", "/registrar-admin", "/eliminar-usuario/**", 
                                "/eliminar-servicio/**").hasAuthority("ROLE_ADMINISTRADOR")
                        
                        // ======== ACCESO PARA USUARIOS NORMALES ========
                        // Rutas específicas para usuarios normales
                        .requestMatchers("/interfaz-inicio", "/interfaz_inicio", "/registrar-servicio", 
                                "/lineas-atencion", "/gestionar-servicio", "/consejos-personzalidos", "/inicio",
                                "/cortes", "/datos-personales", "/cambiar-contrasena", "/mis-servicios", 
                                "/calcular-consumo", "/usuarios/cambiar-contrasena", "/usuarios/actualizar/**", 
                                "/reportar-falla").hasAuthority("ROLE_USUARIO")
                        
                        // Controlador de servicios (todos los métodos)
                        .requestMatchers("/servicios/**").hasAuthority("ROLE_USUARIO")
                        
                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureHandler(customAuthenticationFailureHandler())  // Usar el manejador personalizado
                        .successHandler(customSuccessHandler)
                        .permitAll())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService))
                        .successHandler(customOAuth2SuccessHandler())
                        .failureHandler(customOAuth2FailureHandler())  // Añadir manejador de error OAuth2
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customOAuth2SuccessHandler() {
        return new CustomOAuth2SuccessHandler(usuarioRepository);
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationFailureHandler customOAuth2FailureHandler() {
        return new CustomOAuth2FailureHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder()); // Usa el encoder configurado
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
