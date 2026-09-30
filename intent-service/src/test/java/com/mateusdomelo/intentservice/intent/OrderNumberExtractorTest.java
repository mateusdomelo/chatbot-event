package com.mateusdomelo.intentservice.intent;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderNumberExtractorTest {

    @Test
    void regexExtraiNumeroPedido() {
        OrderNumberExtractor extractor = new OrderNumberExtractor();
        assertEquals(Optional.of(123), extractor.extract("Oi, pedido 123"));
    }
}