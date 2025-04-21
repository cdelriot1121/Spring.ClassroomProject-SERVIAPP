package com.example.ServiApp.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Service

public class CaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
        "https://www.google.com/recaptcha/api/siteverify";

    public boolean verifyCaptcha(String response) {
        RestTemplate restTemplate = new RestTemplate();

        String verifyUrl = GOOGLE_RECAPTCHA_VERIFY_URL +
            "?secret=" + recaptchaSecret +
            "&response=" + response;

        ResponseEntity<Map> captchaResponse =
            restTemplate.postForEntity(verifyUrl, null, Map.class);

        Map<String, Object> body = captchaResponse.getBody();
        return (Boolean) body.get("success");
    }

}
