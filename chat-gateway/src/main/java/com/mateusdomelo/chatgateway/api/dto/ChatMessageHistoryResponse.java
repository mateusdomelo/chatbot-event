package com.mateusdomelo.chatgateway.api.dto;

import com.mateusdomelo.chatgateway.domain.ChatMessage;

import java.util.List;

public record ChatMessageHistoryResponse(
        List<ChatMessage> messages
) { }
