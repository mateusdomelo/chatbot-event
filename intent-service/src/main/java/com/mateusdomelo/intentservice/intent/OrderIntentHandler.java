package com.mateusdomelo.intentservice.intent;

import com.mateusdomelo.intentservice.domain.Order;
import com.mateusdomelo.intentservice.repository.OrderRepository;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@org.springframework.core.annotation.Order(Ordered.HIGHEST_PRECEDENCE)
public class OrderIntentHandler implements IntentHandler {

    private final OrderRepository orderRepository;
    private final OrderNumberExtractor orderNumberExtractor;

    public OrderIntentHandler(OrderRepository orderRepository, OrderNumberExtractor orderNumberExtractor) {
        this.orderRepository = orderRepository;
        this.orderNumberExtractor = orderNumberExtractor;
    }

    @Override
    public Optional<String> handle(String message) {
        Optional<Integer> orderNumberOpt = orderNumberExtractor.extract(message);

        if (orderNumberOpt.isPresent()) {
            Optional<Order> orderOpt = orderRepository.findByOrderNumber(orderNumberOpt.get());

            if (orderOpt.isPresent()) {
                Order order = orderOpt.get();
                return Optional.of("Your order '" + order.getOrderNumber() + "' is '" + order.getStatus() + "'.");
            }

            return Optional.of("I can not find your order. Try again.");
        }

        return Optional.empty();
    }
}
