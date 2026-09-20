package com.mateusdomelo.intentservice.messaging;

import com.mateusdomelo.intentservice.intent.IntentPipeline;
import com.mateusdomelo.intentservice.messaging.event.ChatMessageEvent;
import com.mateusdomelo.intentservice.messaging.event.ChatReplyEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ChatMessageListener {
    private final ChatReplyProducer chatReplyProducer;
    private final IntentPipeline intentPipeline;

    public ChatMessageListener(ChatReplyProducer chatReplyProducer, IntentPipeline intentPipeline) {
        this.chatReplyProducer = chatReplyProducer;
        this.intentPipeline = intentPipeline;
    }

    @KafkaListener(topics = "${app.topics.messages}", groupId = "${spring.kafka.consumer.group-id}")
    public void receiveMessage(ChatMessageEvent chatMessageEvent){
        System.out.println("[INTENT]: Message received: " + chatMessageEvent);

        var intentReply = intentPipeline.execute(chatMessageEvent.message());

        ChatReplyEvent reply = new ChatReplyEvent(chatMessageEvent.sessionId(),
                "(LuIA): " + intentReply, Instant.now());

        chatReplyProducer.send(reply);
    }
}
