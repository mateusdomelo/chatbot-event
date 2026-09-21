package com.mateusdomelo.chatgateway.messaging;

import com.mateusdomelo.chatgateway.domain.ChatMessage;
import com.mateusdomelo.chatgateway.messaging.event.ChatReplyEvent;
import com.mateusdomelo.chatgateway.repository.ChatMessageRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ChatReplyListener {
    private final ChatMessageRepository chatMessageRepository;

    public ChatReplyListener(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @KafkaListener(topics = "${app.topics.replies}", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveMessage(ChatReplyEvent chatReplyEvent) {
        System.out.println("[CHAT GATEWAY] Reply | " + chatReplyEvent.message());

        chatMessageRepository.save(
                new ChatMessage(chatReplyEvent.sessionId(), "bot", chatReplyEvent.message(), Instant.now()));
    }
}
