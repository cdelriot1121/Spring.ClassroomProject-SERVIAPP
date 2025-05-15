package com.example.ServiApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Configuración de Redis para el manejo de sesiones distribuidas.
 * Permite almacenar las sesiones de usuario en Redis en lugar de la memoria local del servidor,
 * facilitando la escalabilidad horizontal de la aplicación.
 */
@Configuration
@EnableRedisHttpSession // Habilita el manejo de sesiones HTTP utilizando Redis
public class RedisSessionConfig {

    /**
     * Define el serializador predeterminado que Spring Session usará para almacenar objetos en Redis.
     * 
     * Se utiliza {@code RedisSerializer.java()} para serializar objetos Java de forma predeterminada.
     * Esto es necesario cuando se utilizan clases personalizadas (como usuarios OAuth2) que deben ser serializables.
     *
     * @return el serializador de objetos Java para Redis
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return RedisSerializer.java();
    }
}
