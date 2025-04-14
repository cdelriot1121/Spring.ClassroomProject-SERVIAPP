package com.example.ServiApp.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passworGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = "Usuario123@";  // Usa una contraseña que cumpla con el patrón de tu formulario
        
        String hashedPassword = encoder.encode(password);
        System.out.println("Contraseña original: " + password);
        System.out.println("Contraseña encriptada: " + hashedPassword);
        
        // Verifica si la encriptación funciona correctamente
        boolean matches = encoder.matches(password, hashedPassword);
        System.out.println("¿La contraseña coincide con el hash?: " + matches);
        
        // Verifica con una contraseña incorrecta para confirmar
        boolean shouldBeFalse = encoder.matches("ContraseñaIncorrecta", hashedPassword);
        System.out.println("¿La contraseña incorrecta coincide?: " + shouldBeFalse);
    }
}
