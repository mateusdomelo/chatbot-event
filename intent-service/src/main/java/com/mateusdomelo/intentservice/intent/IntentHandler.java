package com.mateusdomelo.intentservice.intent;

import java.util.Optional;

public interface IntentHandler {
    Optional<String> handle(String message);
}
