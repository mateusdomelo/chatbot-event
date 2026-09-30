package com.mateusdomelo.intentservice.messaging;

import com.mateusdomelo.intentservice.intent.IntentPipeline;
import com.mateusdomelo.intentservice.messaging.event.ChatMessageEvent;
import com.mateusdomelo.intentservice.messaging.event.ChatReplyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ChatMessageListener {
    private final ChatReplyProducer chatReplyProducer;
    private final IntentPipeline intentPipeline;

    private static final Logger logger = LoggerFactory.getLogger(ChatMessageListener.class);

    public ChatMessageListener(ChatReplyProducer chatReplyProducer, IntentPipeline intentPipeline) {
        this.chatReplyProducer = chatReplyProducer;
        this.intentPipeline = intentPipeline;
    }

    @KafkaListener(topics = "${app.topics.messages}", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveMessage(ChatMessageEvent chatMessageEvent){
        logger.info("[INTENT]: Message received: {}", chatMessageEvent);

        var intentReply = intentPipeline.execute(chatMessageEvent.message());

        ChatReplyEvent reply = new ChatReplyEvent(chatMessageEvent.sessionId(),intentReply, Instant.now());

        chatReplyProducer.send(reply);
    }
}
