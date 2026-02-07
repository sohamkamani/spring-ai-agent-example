package com.example.support.config;


import com.example.support.model.GetOrderRequest;
import com.example.support.model.Order;
import com.example.support.model.ProcessRefundRequest;
import com.example.support.model.RefundResult;
import com.example.support.service.OrderService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class AgentConfig {

    @Bean
    @Description("Get order details by order ID")
    public Function<GetOrderRequest, Order> getOrderDetails(OrderService orderService) {
        return request -> orderService.findById(request.orderId());
    }

    @Bean
    @Description("Process a refund for a delivered order")
    public Function<ProcessRefundRequest, RefundResult> processRefund(OrderService orderService) {
        return request -> orderService.processRefund(request.orderId());
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("You are a helpful customer support agent. " +
                        "You can help customers with order inquiries and process refunds for delivered orders. " +
                        "Always be polite and professional.")
                .defaultFunctions("getOrderDetails", "processRefund")
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }
}
