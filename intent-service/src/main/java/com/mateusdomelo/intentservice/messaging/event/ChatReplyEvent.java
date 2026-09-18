package com.mateusdomelo.intentservice.messaging.event;

import java.time.Instant;

public record ChatReplyEvent (
        String sessionId,
        String message,
        Instant sentAt
) {}
