package com.mateusdomelo.chatgateway.api;

import com.mateusdomelo.chatgateway.api.dto.SendMessageRequest;
import com.mateusdomelo.chatgateway.api.dto.SendMessageResponse;
import com.mateusdomelo.chatgateway.domain.ChatMessage;
import com.mateusdomelo.chatgateway.messaging.ChatMessageProducer;
import com.mateusdomelo.chatgateway.messaging.event.ChatMessageEvent;
import com.mateusdomelo.chatgateway.repository.ChatMessageRepository;
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
    private final ChatMessageRepository chatMessageRepository;

    public ChatController(ChatMessageProducer chatMessageProducer, ChatMessageRepository chatMessageRepository) {
        this.chatMessageProducer = chatMessageProducer;
        this.chatMessageRepository = chatMessageRepository;
    }

    @PostMapping
    public ResponseEntity<SendMessageResponse> sendMessage(@Valid @RequestBody SendMessageRequest request) {
        Instant sentAt = Instant.now();

        chatMessageProducer.send(new ChatMessageEvent(request.sessionId(), request.message(), sentAt));
        chatMessageRepository.save(new ChatMessage(request.sessionId(), "user", request.message(), sentAt));

        return ResponseEntity.accepted().body(new SendMessageResponse(request.sessionId(), request.message(), sentAt));
    }
}
