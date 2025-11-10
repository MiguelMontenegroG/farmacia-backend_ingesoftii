package com.farmacia.repository;

import com.farmacia.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {

    List<ChatMessage> findBySessionIdOrderByTimestampAsc(String sessionId);

    List<ChatMessage> findBySenderIdOrRecipientIdOrderByTimestampDesc(String senderId, String recipientId);

    List<ChatMessage> findBySessionIdAndTimestampAfterOrderByTimestampAsc(String sessionId, LocalDateTime timestamp);

    @Query("{ 'sessionId': ?0, 'read': false, 'recipientId': ?1 }")
    List<ChatMessage> findUnreadMessagesBySessionAndRecipient(String sessionId, String recipientId);

    long countBySessionIdAndReadFalseAndRecipientId(String sessionId, String recipientId);

    List<ChatMessage> findBySessionIdAndRecipientIdAndReadFalse(String sessionId, String recipientId);
}