package com.farmacia.service.impl;

import com.farmacia.model.ChatMessage;
import com.farmacia.model.Usuario;
import com.farmacia.repository.ChatMessageRepository;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public ChatMessage saveMessage(ChatMessage message) {
        // Asegurar que los nombres estén actualizados
        if (message.getSenderId() != null) {
            Usuario sender = usuarioRepository.findById(message.getSenderId()).orElse(null);
            if (sender != null) {
                message.setSenderName(sender.getNombre() + " " + sender.getApellido());
            }
        }

        if (message.getRecipientId() != null) {
            Usuario recipient = usuarioRepository.findById(message.getRecipientId()).orElse(null);
            if (recipient != null) {
                message.setRecipientName(recipient.getNombre() + " " + recipient.getApellido());
            }
        }

        return chatMessageRepository.save(message);
    }

    @Override
    public List<ChatMessage> getMessagesBySession(String sessionId) {
        return chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
    }

    @Override
    public List<ChatMessage> getRecentMessagesForUser(String userId) {
        return chatMessageRepository.findBySenderIdOrRecipientIdOrderByTimestampDesc(userId, userId)
                .stream()
                .limit(50)
                .collect(Collectors.toList());
    }

    @Override
    public void markMessagesAsRead(String sessionId, String userId) {
        List<ChatMessage> unreadMessages = chatMessageRepository.findBySessionIdAndRecipientIdAndReadFalse(sessionId, userId);
        unreadMessages.forEach(message -> {
            message.setRead(true);
            chatMessageRepository.save(message);
        });
    }

    @Override
    public String createChatSession(String clientId, String advisorId) {
        String sessionId = UUID.randomUUID().toString();

        // Crear mensaje de sistema para iniciar la sesión
        ChatMessage systemMessage = new ChatMessage(
                sessionId,
                "SYSTEM",
                clientId,
                "Sistema",
                "Cliente",
                "Sesión de chat iniciada",
                ChatMessage.MessageType.SYSTEM
        );

        chatMessageRepository.save(systemMessage);

        return sessionId;
    }

    @Override
    public List<String> getActiveSessionsForUser(String userId) {
        // Obtener sesiones únicas donde el usuario es sender o recipient
        return chatMessageRepository.findBySenderIdOrRecipientIdOrderByTimestampDesc(userId, userId)
                .stream()
                .map(ChatMessage::getSessionId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public void endChatSession(String sessionId) {
        // Crear mensaje de sistema para finalizar la sesión
        ChatMessage endMessage = new ChatMessage(
                sessionId,
                "SYSTEM",
                "ALL",
                "Sistema",
                "Todos",
                "Sesión de chat finalizada",
                ChatMessage.MessageType.SYSTEM
        );

        chatMessageRepository.save(endMessage);
    }

    @Override
    public int getUnreadMessageCount(String sessionId, String userId) {
        return (int) chatMessageRepository.countBySessionIdAndReadFalseAndRecipientId(sessionId, userId);
    }

    @Override
    public List<ChatMessage> getUnreadMessages(String sessionId, String userId) {
        return chatMessageRepository.findUnreadMessagesBySessionAndRecipient(sessionId, userId);
    }

    @Override
    public List<ChatMessage> searchMessages(String sessionId, String keyword) {
        // Esta es una implementación básica. En producción, se podría usar índices de texto
        return chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId)
                .stream()
                .filter(message -> message.getContent() != null &&
                        message.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}