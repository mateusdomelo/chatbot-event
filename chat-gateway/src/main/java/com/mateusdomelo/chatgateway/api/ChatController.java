package com.mateusdomelo.chatgateway.api;

import com.mateusdomelo.chatgateway.api.dto.SendMessageRequest;
import com.mateusdomelo.chatgateway.api.dto.SendMessageResponse;
import com.mateusdomelo.chatgateway.messaging.ChatMessageProducer;
import com.mateusdomelo.chatgateway.messaging.event.ChatMessageEvent;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatMessageProducer chatMessageProducer;

    public ChatController(ChatMessageProducer chatMessageProducer) {
        this.chatMessageProducer = chatMessageProducer;
    }

    @PostMapping
    public ResponseEntity<SendMessageResponse> sendMessage(@Valid @RequestBody SendMessageRequest sendMessageRequest) {
        ChatMessageEvent messageEvent = new ChatMessageEvent(
                sendMessageRequest.sessionId(),
                sendMessageRequest.message(),
                Instant.now()
        );

        chatMessageProducer.send(messageEvent);

        return new ResponseEntity<>(new SendMessageResponse(
                messageEvent.message(),
                messageEvent.sentAt()
        ), HttpStatus.OK);
    }
}
