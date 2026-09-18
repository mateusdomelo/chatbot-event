package com.mateusdomelo.intentservice.messaging;

import com.mateusdomelo.intentservice.messaging.event.ChatReplyEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ChatReplyProducer {

    private final KafkaTemplate<String, ChatReplyEvent> kafkaTemplate;

    private final String topic;

    public ChatReplyProducer(KafkaTemplate<String, ChatReplyEvent> kafkaTemplate,
                             @Value("${app.topics.replies}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void send(ChatReplyEvent chatReplyEvent) {
        kafkaTemplate.send(topic, chatReplyEvent);
        System.out.println("[INTENT]: Mensagem enviada ao tópico '" + topic + "'");
    }
}
