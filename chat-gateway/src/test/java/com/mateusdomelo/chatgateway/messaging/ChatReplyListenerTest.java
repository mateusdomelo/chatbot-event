package com.mateusdomelo.chatgateway.messaging;

import com.mateusdomelo.chatgateway.domain.ChatMessage;
import com.mateusdomelo.chatgateway.messaging.event.ChatReplyEvent;
import com.mateusdomelo.chatgateway.repository.ChatMessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ChatReplyListenerTest {
    private ChatMessageRepository chatMessageRepository;
    private ChatReplyListener chatReplyListener;

    @BeforeEach
    void setup() {
        chatMessageRepository = mock(ChatMessageRepository.class);
        chatReplyListener = new ChatReplyListener(chatMessageRepository);
    }

    @Test
    void deveReceberEventoESalvarHistoricoDeMensagem() {
        ChatReplyEvent replyEvent = new ChatReplyEvent("abc", "Order 321 with 'pending' status'", Instant.now());

        chatReplyListener.receiveMessage(replyEvent);

        ArgumentCaptor<ChatMessage> messageCaptor = ArgumentCaptor.forClass(ChatMessage.class);
        verify(chatMessageRepository).save(messageCaptor.capture());
        assertEquals(replyEvent.sessionId(), messageCaptor.getValue().getSessionId());
        assertEquals("bot", messageCaptor.getValue().getSender());
        assertEquals(replyEvent.message(), messageCaptor.getValue().getText());
    }
}
