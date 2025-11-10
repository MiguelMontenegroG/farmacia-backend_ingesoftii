package com.farmacia.controller;

import com.farmacia.model.ChatMessage;
import com.farmacia.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage/{sessionId}")
    @SendTo("/topic/chat/{sessionId}")
    public ChatMessage sendMessage(@DestinationVariable String sessionId,
                                  @Payload ChatMessage chatMessage,
                                  SimpMessageHeaderAccessor headerAccessor) {

        // Guardar el mensaje en la base de datos
        ChatMessage savedMessage = chatService.saveMessage(chatMessage);

        // Notificar al destinatario específico si es un mensaje privado
        if (savedMessage.getRecipientId() != null && !savedMessage.getRecipientId().equals("ALL")) {
            messagingTemplate.convertAndSendToUser(
                    savedMessage.getRecipientId(),
                    "/queue/messages",
                    savedMessage
            );
        }

        return savedMessage;
    }

    @MessageMapping("/chat.addUser/{sessionId}")
    @SendTo("/topic/chat/{sessionId}")
    public ChatMessage addUser(@DestinationVariable String sessionId,
                              @Payload ChatMessage chatMessage,
                              SimpMessageHeaderAccessor headerAccessor) {

        // Agregar información del usuario a la sesión
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderName());
        headerAccessor.getSessionAttributes().put("sessionId", sessionId);

        // Crear mensaje de unión
        ChatMessage joinMessage = new ChatMessage(
                sessionId,
                chatMessage.getSenderId(),
                "ALL",
                chatMessage.getSenderName(),
                "Todos",
                chatMessage.getSenderName() + " se unió al chat",
                ChatMessage.MessageType.JOIN
        );

        return chatService.saveMessage(joinMessage);
    }

    @MessageMapping("/chat.typing/{sessionId}")
    @SendTo("/topic/chat/{sessionId}")
    public ChatMessage userTyping(@DestinationVariable String sessionId,
                                 @Payload ChatMessage chatMessage) {

        ChatMessage typingMessage = new ChatMessage(
                sessionId,
                chatMessage.getSenderId(),
                "ALL",
                chatMessage.getSenderName(),
                "Todos",
                chatMessage.getSenderName() + " está escribiendo...",
                ChatMessage.MessageType.TYPING
        );

        return typingMessage; // No guardar mensajes de typing
    }

    @MessageMapping("/chat.leave/{sessionId}")
    @SendTo("/topic/chat/{sessionId}")
    public ChatMessage leaveChat(@DestinationVariable String sessionId,
                                @Payload ChatMessage chatMessage) {

        ChatMessage leaveMessage = new ChatMessage(
                sessionId,
                chatMessage.getSenderId(),
                "ALL",
                chatMessage.getSenderName(),
                "Todos",
                chatMessage.getSenderName() + " abandonó el chat",
                ChatMessage.MessageType.LEAVE
        );

        return chatService.saveMessage(leaveMessage);
    }

    @MessageMapping("/chat.markAsRead/{sessionId}")
    public void markAsRead(@DestinationVariable String sessionId,
                          @Payload String userId) {

        chatService.markMessagesAsRead(sessionId, userId);

        // Notificar que los mensajes fueron marcados como leídos
        messagingTemplate.convertAndSend(
                "/topic/chat/" + sessionId + "/read",
                userId
        );
    }
}