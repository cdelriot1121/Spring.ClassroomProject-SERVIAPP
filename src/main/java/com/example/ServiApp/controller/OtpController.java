package com.example.ServiApp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ServiApp.services.OtpService;

@RestController
@RequestMapping("/otp")
public class OtpController {

private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<Void> enviarOtp(@RequestParam String email) {
        otpService.generarYEnviarOtp(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verificar")
    public ResponseEntity<Boolean> verificarOtp(@RequestParam String email, @RequestParam String otp) {
        boolean valido = otpService.verificarOtp(email, otp);
        return ResponseEntity.ok(valido);
    }





    }




