package com.mateusdomelo.chatgateway.repository;

import com.mateusdomelo.chatgateway.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
