package com.farmacia.service;

import com.farmacia.model.ChatMessage;
import com.farmacia.model.Usuario;
import com.farmacia.repository.ChatMessageRepository;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.service.impl.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ChatServiceImpl chatService;

    private ChatMessage message1;
    private ChatMessage message2;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId("user1");
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");

        message1 = new ChatMessage();
        message1.setId("msg1");
        message1.setSessionId("session1");
        message1.setSenderId("user1");
        message1.setRecipientId("user2");
        message1.setContent("Hola");
        message1.setType(ChatMessage.MessageType.TEXT);

        message2 = new ChatMessage();
        message2.setId("msg2");
        message2.setSessionId("session1");
        message2.setSenderId("user2");
        message2.setRecipientId("user1");
        message2.setContent("Hola, ¿cómo estás?");
        message2.setType(ChatMessage.MessageType.TEXT);
    }

    @Test
    void testSaveMessage() {
        // Given
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(message1);
        when(usuarioRepository.findById("user1")).thenReturn(Optional.of(usuario));

        // When
        ChatMessage result = chatService.saveMessage(message1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getSenderName()).isEqualTo("Juan Pérez");
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
    }

    @Test
    void testGetMessagesBySession() {
        // Given
        List<ChatMessage> messages = Arrays.asList(message1, message2);
        when(chatMessageRepository.findBySessionIdOrderByTimestampAsc("session1")).thenReturn(messages);

        // When
        List<ChatMessage> result = chatService.getMessagesBySession("session1");

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).contains(message1, message2);
        verify(chatMessageRepository, times(1)).findBySessionIdOrderByTimestampAsc("session1");
    }

    @Test
    void testGetRecentMessagesForUser() {
        // Given
        List<ChatMessage> messages = Arrays.asList(message1, message2);
        when(chatMessageRepository.findBySenderIdOrRecipientIdOrderByTimestampDesc("user1", "user1"))
                .thenReturn(messages);

        // When
        List<ChatMessage> result = chatService.getRecentMessagesForUser("user1");

        // Then
        assertThat(result).hasSize(2);
        verify(chatMessageRepository, times(1)).findBySenderIdOrRecipientIdOrderByTimestampDesc("user1", "user1");
    }

    @Test
    void testMarkMessagesAsRead() {
        // Given
        List<ChatMessage> unreadMessages = Arrays.asList(message1);
        when(chatMessageRepository.findBySessionIdAndRecipientIdAndReadFalse("session1", "user1"))
                .thenReturn(unreadMessages);

        // When
        chatService.markMessagesAsRead("session1", "user1");

        // Then
        verify(chatMessageRepository, times(1)).findBySessionIdAndRecipientIdAndReadFalse("session1", "user1");
        verify(chatMessageRepository, times(1)).save(message1);
        assertThat(message1.isRead()).isTrue();
    }

    @Test
    void testCreateChatSession() {
        // Given
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(new ChatMessage());

        // When
        String sessionId = chatService.createChatSession("client1", "advisor1");

        // Then
        assertThat(sessionId).isNotNull();
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
    }

    @Test
    void testGetActiveSessionsForUser() {
        // Given
        List<ChatMessage> messages = Arrays.asList(message1, message2);
        when(chatMessageRepository.findBySenderIdOrRecipientIdOrderByTimestampDesc("user1", "user1"))
                .thenReturn(messages);

        // When
        List<String> sessions = chatService.getActiveSessionsForUser("user1");

        // Then
        assertThat(sessions).hasSize(1);
        assertThat(sessions).contains("session1");
    }

    @Test
    void testEndChatSession() {
        // Given
        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(new ChatMessage());

        // When
        chatService.endChatSession("session1");

        // Then
        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class));
    }

    @Test
    void testGetUnreadMessageCount() {
        // Given
        when(chatMessageRepository.countBySessionIdAndReadFalseAndRecipientId("session1", "user1")).thenReturn(3L);

        // When
        int count = chatService.getUnreadMessageCount("session1", "user1");

        // Then
        assertThat(count).isEqualTo(3);
        verify(chatMessageRepository, times(1)).countBySessionIdAndReadFalseAndRecipientId("session1", "user1");
    }

    @Test
    void testGetUnreadMessages() {
        // Given
        List<ChatMessage> unreadMessages = Arrays.asList(message1);
        when(chatMessageRepository.findUnreadMessagesBySessionAndRecipient("session1", "user1"))
                .thenReturn(unreadMessages);

        // When
        List<ChatMessage> result = chatService.getUnreadMessages("session1", "user1");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result).contains(message1);
        verify(chatMessageRepository, times(1)).findUnreadMessagesBySessionAndRecipient("session1", "user1");
    }

    @Test
    void testSearchMessages() {
        // Given
        List<ChatMessage> allMessages = Arrays.asList(message1, message2);
        when(chatMessageRepository.findBySessionIdOrderByTimestampAsc("session1")).thenReturn(allMessages);

        // When
        List<ChatMessage> result = chatService.searchMessages("session1", "Hola");

        // Then
        assertThat(result).hasSize(2); // Ambos mensajes contienen "Hola"
        verify(chatMessageRepository, times(1)).findBySessionIdOrderByTimestampAsc("session1");
    }
}