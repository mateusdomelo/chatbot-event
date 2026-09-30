package com.mateusdomelo.chatgateway.api;

import com.mateusdomelo.chatgateway.api.dto.SendMessageRequest;
import com.mateusdomelo.chatgateway.api.dto.SendMessageResponse;
import com.mateusdomelo.chatgateway.domain.ChatMessage;
import com.mateusdomelo.chatgateway.messaging.ChatMessageProducer;
import com.mateusdomelo.chatgateway.messaging.event.ChatMessageEvent;
import com.mateusdomelo.chatgateway.repository.ChatMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class ChatControllerTest {
    private ChatMessageProducer chatMessageProducer;
    private ChatMessageRepository chatMessageRepository;
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        chatMessageProducer = Mockito.mock(ChatMessageProducer.class);
        chatMessageRepository = Mockito.mock(ChatMessageRepository.class);
        chatController = new ChatController(chatMessageProducer, chatMessageRepository);
    }

    @Test
    void deveRetornar202EPersistirMensagemAoReceberRequisicaoValida() {
        String sessionId = "abc";
        String message = "Ola, pedido 123?";

        SendMessageRequest request = new SendMessageRequest(sessionId, message);
        ResponseEntity<SendMessageResponse> response = chatController.sendMessage(request);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sessionId, response.getBody().sessionId());
        assertEquals(message, response.getBody().message());
        assertNotNull(response.getBody().sentAt());

        ArgumentCaptor<ChatMessageEvent> eventCaptor = ArgumentCaptor.forClass(ChatMessageEvent.class);
        Mockito.verify(chatMessageProducer).send(eventCaptor.capture());
        assertEquals(sessionId, eventCaptor.getValue().sessionId());
        assertEquals(message, eventCaptor.getValue().message());

        ArgumentCaptor<ChatMessage> messageCaptor = ArgumentCaptor.forClass(ChatMessage.class);
        Mockito.verify(chatMessageRepository).save(messageCaptor.capture());
        assertEquals(sessionId, messageCaptor.getValue().getSessionId());
        assertEquals("user", messageCaptor.getValue().getSender());
        assertEquals(message, messageCaptor.getValue().getText());

    }
}