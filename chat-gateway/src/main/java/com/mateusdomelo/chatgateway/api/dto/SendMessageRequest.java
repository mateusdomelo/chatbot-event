package com.mateusdomelo.chatgateway.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SendMessageRequest(
        @NotBlank
        String sessionId,

        @NotBlank
        @Size(min = 10, max = 400)
        String message
) {
}
