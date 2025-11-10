package com.farmacia.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "chat_messages")
public class ChatMessage {
    @Id
    private String id;

    @Indexed
    private String sessionId; // ID de la sesión de chat

    @Indexed
    private String senderId; // ID del usuario que envía

    @Indexed
    private String recipientId; // ID del usuario que recibe (asesor o cliente)

    private String senderName;
    private String recipientName;
    private String content;
    private MessageType type; // TEXT, JOIN, LEAVE, TYPING
    private LocalDateTime timestamp;
    private boolean read;

    public enum MessageType {
        TEXT,
        JOIN,
        LEAVE,
        TYPING,
        SYSTEM
    }

    // Constructores
    public ChatMessage() {}

    public ChatMessage(String sessionId, String senderId, String recipientId,
                      String senderName, String recipientName, String content,
                      MessageType type) {
        this.sessionId = sessionId;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now();
        this.read = false;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getSenderId() { return senderId; }
    public void setSenderId(String senderId) { this.senderId = senderId; }

    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getRecipientName() { return recipientName; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}