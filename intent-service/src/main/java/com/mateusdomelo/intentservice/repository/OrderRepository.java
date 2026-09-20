package com.mateusdomelo.intentservice.repository;

import com.mateusdomelo.intentservice.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(Integer orderNumber);
}
