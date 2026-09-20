package com.mateusdomelo.intentservice.intent;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IntentPipeline {
    private final List<IntentHandler> handlers;

    public IntentPipeline(List<IntentHandler> handlers) {
        this.handlers = handlers;
    }

    public String execute(String message) {
        return handlers.stream()
                .map(handler -> handler.handle(message))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("None handler accepted the message"));

    }
}
