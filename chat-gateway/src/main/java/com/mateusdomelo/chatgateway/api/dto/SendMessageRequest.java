package com.mateusdomelo.chatgateway.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SendMessageRequest(
        @NotNull
        String sessionId,

        @NotNull
        @Size(min = 10, max = 400)
        String message
) {
}
