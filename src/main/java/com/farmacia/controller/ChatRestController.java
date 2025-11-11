package com.farmacia.controller;

import com.farmacia.model.ChatMessage;
import com.farmacia.service.ChatService;
import com.farmacia.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/session")
    public ResponseEntity<Map<String, String>> createChatSession(
            @RequestParam String clientId,
            @RequestParam String advisorId) {

        String sessionId = chatService.createChatSession(clientId, advisorId);

        return ResponseEntity.ok(Map.of(
                "sessionId", sessionId,
                "message", "Sesión de chat creada exitosamente"
        ));
    }

    @GetMapping("/session/{sessionId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable String sessionId) {
        List<ChatMessage> messages = chatService.getMessagesBySession(sessionId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/user/{userId}/messages")
    public ResponseEntity<List<ChatMessage>> getUserMessages(@PathVariable String userId) {
        List<ChatMessage> messages = chatService.getRecentMessagesForUser(userId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/session/{sessionId}/unread/{userId}")
    public ResponseEntity<List<ChatMessage>> getUnreadMessages(
            @PathVariable String sessionId,
            @PathVariable String userId) {

        List<ChatMessage> unreadMessages = chatService.getUnreadMessages(sessionId, userId);
        return ResponseEntity.ok(unreadMessages);
    }

    @GetMapping("/session/{sessionId}/unread/{userId}/count")
    public ResponseEntity<Map<String, Integer>> getUnreadMessageCount(
            @PathVariable String sessionId,
            @PathVariable String userId) {

        int count = chatService.getUnreadMessageCount(sessionId, userId);
        return ResponseEntity.ok(Map.of("unreadCount", count));
    }

    @PutMapping("/session/{sessionId}/markRead/{userId}")
    public ResponseEntity<ApiResponse<String>> markMessagesAsRead(
            @PathVariable String sessionId,
            @PathVariable String userId) {

        chatService.markMessagesAsRead(sessionId, userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Mensajes marcados como leídos", null));
    }

    @GetMapping("/user/{userId}/sessions")
    public ResponseEntity<List<String>> getUserSessions(@PathVariable String userId) {
        List<String> sessions = chatService.getActiveSessionsForUser(userId);
        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/session/{sessionId}/end")
    public ResponseEntity<ApiResponse<String>> endChatSession(@PathVariable String sessionId) {
        chatService.endChatSession(sessionId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Sesión de chat finalizada", null));
    }

    @GetMapping("/session/{sessionId}/search")
    public ResponseEntity<List<ChatMessage>> searchMessages(
            @PathVariable String sessionId,
            @RequestParam String keyword) {

        List<ChatMessage> messages = chatService.searchMessages(sessionId, keyword);
        return ResponseEntity.ok(messages);
    }
}