package com.example.ServiApp.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passworGenerator {


    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode("admin123");
        System.out.println("Contraseña encriptada: " + hashedPassword);
    }
}
