package com.mateusdomelo.intentservice.messaging;

import com.mateusdomelo.intentservice.messaging.event.ChatMessageEvent;
import com.mateusdomelo.intentservice.messaging.event.ChatReplyEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class ChatMessageListener {
    private final ChatReplyProducer chatReplyProducer;

    public ChatMessageListener(ChatReplyProducer chatReplyProducer) {
        this.chatReplyProducer = chatReplyProducer;
    }

    @KafkaListener(topics = "${app.topics.messages}", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveMessage(ChatMessageEvent chatMessageEvent){
        System.out.println("[INTENT]: Message received: " + chatMessageEvent);
        String sessionId = UUID.randomUUID().toString();
        ChatReplyEvent reply = new ChatReplyEvent(chatMessageEvent.sessionId(),
                "(BLIZ) You said: " + chatMessageEvent.message(), Instant.now());
        chatReplyProducer.send(reply);
    }
}
