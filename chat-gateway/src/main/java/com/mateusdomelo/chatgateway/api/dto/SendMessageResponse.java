package com.mateusdomelo.chatgateway.api.dto;

public record SendMessageResponse(
        String sessionId,
        String message,
        java.time.Instant sentAt
) {
}
