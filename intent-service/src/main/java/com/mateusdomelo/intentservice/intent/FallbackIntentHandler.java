package com.mateusdomelo.intentservice.intent;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FallbackIntentHandler implements IntentHandler {
    @Override
    public Optional<String> handle(String message) {
        return Optional.of("Sorry, I did not understand your message. Try again.");
    }
}
