package com.mateusdomelo.chatgateway.api.dto;

public record SendMessageResponse(
        String message,
        java.time.Instant sentAt
) {
}
