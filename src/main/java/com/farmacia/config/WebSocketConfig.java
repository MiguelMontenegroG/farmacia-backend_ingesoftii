package com.farmacia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Configura el broker para mensajes públicos (/topic) y privados (/queue)
        config.enableSimpleBroker("/topic", "/queue");
        // Prefijo para mensajes enviados desde el cliente hacia el servidor
        config.setApplicationDestinationPrefixes("/app");
        // Prefijo para mensajes dirigidos a un usuario específico
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Define el endpoint para conexiones WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOrigins(
                        "https://farmaciafront-gamma.vercel.app", // producción (Vercel)
                        "http://localhost:3000"                   // desarrollo local
                )
                .withSockJS(); // fallback para navegadores sin soporte WebSocket
    }
}
