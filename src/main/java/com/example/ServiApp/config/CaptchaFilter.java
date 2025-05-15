package com.example.ServiApp.config;


import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ServiApp.services.CaptchaService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro que verifica el reCAPTCHA en las solicitudes de inicio de sesión.
 * Intercepta las peticiones POST al endpoint de login para validar la respuesta del captcha.
 */
public class CaptchaFilter extends OncePerRequestFilter {

      private final CaptchaService captchaService;

    public CaptchaFilter(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        if ("/login".equals(request.getServletPath()) && request.getMethod().equalsIgnoreCase("POST")) {
            String captchaResponse = request.getParameter("g-recaptcha-response");

            if (captchaResponse == null || !captchaService.verifyCaptcha(captchaResponse)) {
                response.sendRedirect("/login?captchaError=true");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}