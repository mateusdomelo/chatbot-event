package com.mateusdomelo.intentservice.intent;

import com.mateusdomelo.intentservice.domain.Order;
import com.mateusdomelo.intentservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class IntentPipelineTest {
    private OrderRepository orderRepository;
    private IntentPipeline pipeline;

    @BeforeEach
    public void setup() {
        orderRepository = mock(OrderRepository.class);
        var orderNumberExtractor = new OrderNumberExtractor();

        var orderHandler = new OrderIntentHandler(orderRepository, orderNumberExtractor);
        var fallbackHandler = new FallbackIntentHandler();
        pipeline = new IntentPipeline(List.of(orderHandler, fallbackHandler));

    }

    @Test
    void deveRetornarMsgDeStatusPedidoCorretamente() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderNumber(123);
        order.setStatus("pending");

        when(orderRepository.findByOrderNumber(123))
                .thenReturn(Optional.of(order));

        var result = pipeline.execute("Hey, where is my order 123");

        Assertions.assertEquals("Your order '123' is 'pending'.", result);

    }

    @Test
    void deveRetornarMsgDePedidoNaoEncontrado() {
        when(orderRepository.findByOrderNumber(123))
                .thenReturn(Optional.empty());

        var result = pipeline.execute("Hey, where is my order 123");

        Assertions.assertEquals("I can not find your order. Try again.", result);
    }


    @Test
    void deveRetornarMsgGenericaDeFallback() {
        var result = pipeline.execute("Hi, morning! Where is my order??");

        Assertions.assertEquals("Sorry, I did not understand your message. Try again.", result);
    }
}
