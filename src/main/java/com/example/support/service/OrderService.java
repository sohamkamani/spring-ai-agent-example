package com.example.support.service;

import com.example.support.model.Order;
import com.example.support.model.RefundResult;
import com.example.support.repository.InMemoryOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final InMemoryOrderRepository orderRepository;

    public OrderService(InMemoryOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findById(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public RefundResult processRefund(String orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        
        if (orderOpt.isEmpty()) {
            return new RefundResult(false, "Order not found");
        }
        
        Order order = orderOpt.get();
        
        if (!"DELIVERED".equals(order.status())) {
            return new RefundResult(false, "Order is not delivered yet");
        }
        
        if (orderRepository.isRefunded(orderId)) {
            return new RefundResult(false, "Order already refunded");
        }
        
        boolean success = orderRepository.processRefund(orderId);
        if (success) {
            return new RefundResult(true, "Refund processed successfully");
        } else {
            return new RefundResult(false, "Failed to process refund");
        }
    }
}
