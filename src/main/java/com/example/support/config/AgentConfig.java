package com.example.support.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.support.tools.OrderTools;

@Configuration
public class AgentConfig {

  @Bean
  public ChatClient chatClient(@Qualifier("openAiChatModel") ChatModel chatModel, OrderTools orderTools) {
    return ChatClient.builder(chatModel)
        .defaultSystem("You are a helpful customer support agent. " +
            "You can help customers with order inquiries and process refunds for delivered orders. " +
            "Always be polite and professional.")
        .defaultTools(orderTools)
        .build();
  }
}
