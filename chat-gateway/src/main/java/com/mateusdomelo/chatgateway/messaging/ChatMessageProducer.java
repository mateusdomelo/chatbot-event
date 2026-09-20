package com.mateusdomelo.chatgateway.messaging;

import com.mateusdomelo.chatgateway.messaging.event.ChatMessageEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageProducer {
    private final KafkaTemplate<String, ChatMessageEvent> kafkaTemplate;

    private final String topic;

    public ChatMessageProducer(KafkaTemplate<String, ChatMessageEvent> kafkaTemplate,
                               @Value("${app.topics.messages}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(ChatMessageEvent chatMessageEvent) {
        kafkaTemplate.send(topic, chatMessageEvent);
    }
}
