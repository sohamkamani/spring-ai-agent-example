package com.example.support.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import com.example.support.model.Order;
import com.example.support.model.RefundResult;
import com.example.support.service.OrderService;

@Component
public class OrderTools {

  private final OrderService orderService;

  public OrderTools(OrderService orderService) {
    this.orderService = orderService;
  }

  @Tool(description = "Get order details by order ID")
  public Order getOrderDetails(@ToolParam(description = "The ID of the order to retrieve") String orderId) {
    return orderService.findById(orderId);
  }

  @Tool(description = "Process a refund for a delivered order")
  public RefundResult processRefund(@ToolParam(description = "The ID of the order to refund") String orderId) {
    return orderService.processRefund(orderId);
  }
}
