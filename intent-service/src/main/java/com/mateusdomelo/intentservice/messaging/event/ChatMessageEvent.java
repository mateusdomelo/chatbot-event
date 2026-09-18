package com.mateusdomelo.intentservice.messaging.event;

import java.time.Instant;

public record ChatMessageEvent (
        String sessionId,
        String message,
        Instant sentAt
) {}

