package com.mateusdomelo.chatgateway.messaging;

import com.mateusdomelo.chatgateway.messaging.event.ChatReplyEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ChatReplyListener {

    @KafkaListener(topics = "${app.topics.replies}", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveMessage(ChatReplyEvent chatReplyEvent) {
        System.out.println("[CHAT GATEWAY]: Message received: " + chatReplyEvent.message());
        System.out.println("Message with sessionId '" + chatReplyEvent.sessionId() + "' is DONE!");
    }
}
