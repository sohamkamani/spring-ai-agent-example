package com.example.support.repository;

import com.example.support.model.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryOrderRepository {

    private final ConcurrentHashMap<String, Order> orders = new ConcurrentHashMap<>();
    private final Set<String> refundedOrders = ConcurrentHashMap.newKeySet();

    @PostConstruct
    public void init() {
        orders.put("123", new Order("123", "SHIPPED", 99.99));
        orders.put("456", new Order("456", "DELIVERED", 149.99));
        orders.put("789", new Order("789", "PENDING", 29.99));
    }

    public Optional<Order> findById(String orderId) {
        return Optional.ofNullable(orders.get(orderId));
    }

    public boolean processRefund(String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            return false;
        }
        if (!"DELIVERED".equals(order.status())) {
            return false;
        }
        if (refundedOrders.contains(orderId)) {
            return false;
        }
        return refundedOrders.add(orderId);
    }

    public boolean isRefunded(String orderId) {
        return refundedOrders.contains(orderId);
    }

    public void clearRefundedOrders() {
        refundedOrders.clear();
    }
}
