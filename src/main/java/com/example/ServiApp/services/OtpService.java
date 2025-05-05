package com.example.ServiApp.services;

import java.time.Duration;
import java.util.Random;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;

@Service
public class OtpService {

    private static final long OTP_EXPIRATION_MINUTES = 5;
    private static final long VALIDATION_FLAG_EXPIRATION_MINUTES = 5;

    private final RedisTemplate<String, String> redisTemplate;
    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    public OtpService(RedisTemplate<String, String> redisTemplate, JavaMailSender mailSender,
            TemplateEngine templateEngine) {
        this.redisTemplate = redisTemplate;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void generarYEnviarOtp(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));
        String key = "OTP:" + email;

        redisTemplate.opsForValue().set(key, otp, Duration.ofMinutes(OTP_EXPIRATION_MINUTES));

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(email);
            helper.setSubject("🔐 Tu código de acceso para ServiApp");

            // Contexto para Thymeleaf (variables)
            Context context = new Context();
            context.setVariable("otp", otp);
            context.setVariable("expirationMinutes", OTP_EXPIRATION_MINUTES);
            context.setVariable("logoUrl", "/img_local/logo-google.png");

            // Procesar plantilla HTML
            String htmlContent = templateEngine.process("otp-email", context);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
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
