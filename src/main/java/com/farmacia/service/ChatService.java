package com.farmacia.service;

import com.farmacia.model.ChatMessage;

import java.util.List;

public interface ChatService {

    // Gestión de mensajes
    ChatMessage saveMessage(ChatMessage message);
    List<ChatMessage> getMessagesBySession(String sessionId);
    List<ChatMessage> getRecentMessagesForUser(String userId);
    void markMessagesAsRead(String sessionId, String userId);

    // Gestión de sesiones
    String createChatSession(String clientId, String advisorId);
    List<String> getActiveSessionsForUser(String userId);
    void endChatSession(String sessionId);

    // Notificaciones
    int getUnreadMessageCount(String sessionId, String userId);
    List<ChatMessage> getUnreadMessages(String sessionId, String userId);

    // Búsqueda
    List<ChatMessage> searchMessages(String sessionId, String keyword);
}