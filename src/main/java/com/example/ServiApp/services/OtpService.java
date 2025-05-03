package com.example.ServiApp.services;

import java.time.Duration;
import java.util.Random;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private static final long OTP_EXPIRATION_MINUTES = 5;
    private static final long VALIDATION_FLAG_EXPIRATION_MINUTES = 5;

    private final RedisTemplate<String, String> redisTemplate;
    private final JavaMailSender mailSender;

    public OtpService(RedisTemplate<String, String> redisTemplate, JavaMailSender mailSender) {
        this.redisTemplate = redisTemplate;
        this.mailSender = mailSender;
    }

    public void generarYEnviarOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        String key = "OTP:" + email;

        // Guardar OTP en Redis con expiración
        redisTemplate.opsForValue().set(key, otp, Duration.ofMinutes(OTP_EXPIRATION_MINUTES));

        try {
            // Enviar OTP por correo
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Código de Verificación para ServiApp");
            message.setText("Tu código de verificación es: " + otp + "\nEste código expira en 5 minutos.");
            mailSender.send(message);
        } catch (Exception e) {
            // Si deseas, podrías lanzar una excepción personalizada aquí en lugar de
            // loguear
        }
    }

    public boolean verificarOtp(String email, String otpIngresado) {
        String key = "OTP:" + email;
        String otpGuardado = redisTemplate.opsForValue().get(key);

        if (otpGuardado != null && otpGuardado.equals(otpIngresado)) {
            marcarOtpComoVerificado(email);
            return true;
        }

        return false;
    }

    public void marcarOtpComoVerificado(String email) {
        String validKey = "OTP_VALID:" + email;
        redisTemplate.opsForValue().set(validKey, "true", Duration.ofMinutes(VALIDATION_FLAG_EXPIRATION_MINUTES));
    }

    public boolean estaOtpVerificado(String email) {
        String validKey = "OTP_VALID:" + email;
        String valor = redisTemplate.opsForValue().get(validKey);
        return "true".equals(valor);
    }

    public void limpiarOtp(String email) {
        redisTemplate.delete("OTP:" + email);
        redisTemplate.delete("OTP_VALID:" + email);
    }
}
