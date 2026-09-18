package com.mateusdomelo.chatgateway.messaging.event;

import java.time.Instant;

public record ChatReplyEvent (
        String sessionId,
        String message,
        Instant sentAt
) {}
