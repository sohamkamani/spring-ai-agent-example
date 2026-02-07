package com.example.support;

import com.example.support.model.Order;
import com.example.support.model.RefundResult;
import com.example.support.repository.InMemoryOrderRepository;
import com.example.support.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerSupportAgentTest {

    @Autowired
    private InMemoryOrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository.clearRefundedOrders();
    }

    @Test
    void testOrderLookup_Order123() {
        Optional<Order> order = orderRepository.findById("123");

        assertTrue(order.isPresent());
        assertEquals("123", order.get().id());
        assertEquals("SHIPPED", order.get().status());
        assertEquals(99.99, order.get().amount());
    }

    @Test
    void testOrderLookup_Order456() {
        Optional<Order> order = orderRepository.findById("456");

        assertTrue(order.isPresent());
        assertEquals("456", order.get().id());
        assertEquals("DELIVERED", order.get().status());
        assertEquals(149.99, order.get().amount());
    }

    @Test
    void testOrderLookup_Order789() {
        Optional<Order> order = orderRepository.findById("789");

        assertTrue(order.isPresent());
        assertEquals("789", order.get().id());
        assertEquals("PENDING", order.get().status());
        assertEquals(29.99, order.get().amount());
    }

    @Test
    void testRefund_SuccessForDeliveredOrder() {
        boolean result = orderRepository.processRefund("456");

        assertTrue(result);
        assertTrue(orderRepository.isRefunded("456"));
    }

    @Test
    void testRefund_FailsForNonDeliveredOrder() {
        boolean result = orderRepository.processRefund("123");

        assertFalse(result);
        assertFalse(orderRepository.isRefunded("123"));
    }

    @Test
    void testRefund_FailsForAlreadyRefundedOrder() {
        // First refund should succeed
        boolean firstRefund = orderRepository.processRefund("456");
        assertTrue(firstRefund);

        // Second refund should fail
        boolean secondRefund = orderRepository.processRefund("456");
        assertFalse(secondRefund);
    }

    @Test
    void testOrderService_FindById() {
        Order order = orderService.findById("123");

        assertNotNull(order);
        assertEquals("123", order.id());
        assertEquals("SHIPPED", order.status());
    }

    @Test
    void testOrderService_ProcessRefund_Success() {
        RefundResult result = orderService.processRefund("456");

        assertTrue(result.success());
        assertEquals("Refund processed successfully", result.message());
    }

    @Test
    void testOrderService_ProcessRefund_NotDelivered() {
        RefundResult result = orderService.processRefund("123");

        assertFalse(result.success());
        assertEquals("Order is not delivered yet", result.message());
    }

    @Test
    void testOrderService_ProcessRefund_AlreadyRefunded() {
        // Process first refund
        orderService.processRefund("456");

        // Try to refund again
        RefundResult result = orderService.processRefund("456");

        assertFalse(result.success());
        assertEquals("Order already refunded", result.message());
    }

    @Test
    void testOrderService_ProcessRefund_OrderNotFound() {
        RefundResult result = orderService.processRefund("999");

        assertFalse(result.success());
        assertEquals("Order not found", result.message());
    }
}
