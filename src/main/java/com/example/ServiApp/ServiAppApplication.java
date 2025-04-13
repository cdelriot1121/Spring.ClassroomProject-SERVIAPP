package com.example.ServiApp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.ServiApp.repository.UsuarioRepository;

@SpringBootApplication
public class ServiAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner debugPassword(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepo) {
		return args -> {
			String email = "admin@gmail.com"; // Reemplaza con un email válido de tu BD
			String rawPassword = "admin123"; // Contraseña a verificar

			System.out.println("\n=== 🔍 Iniciando verificación de contraseña ===");

			usuarioRepo.findByEmail(email).ifPresentOrElse(
					usuario -> {
						boolean matches = passwordEncoder.matches(rawPassword, usuario.getPassword());
						System.out.println("🔐 ¿Coincide la contraseña para '" + email + "'? " + matches);
						System.out.println("🔑 Hash almacenado: " + usuario.getPassword());

						if (!matches) {
							System.out.println("⚠️ Posibles causas:");
							System.out.println("- La contraseña en texto plano no es '" + rawPassword + "'");
							System.out.println("- El hash no se generó con BCrypt");
						}
					},
					() -> System.out.println("❌ Usuario no encontrado con email: " + email));

			System.out.println("=== Verificación completada ===\n");
		};
	}
}