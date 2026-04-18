# Spring AI Customer Support Agent

A demo Spring Boot application showcasing an AI-powered customer support agent using Spring AI. The agent can answer customer queries about orders and process refunds using function calling capabilities.

## Features

- AI-powered customer support chat
- Order lookup via function calling
- Refund processing for delivered orders
- Multi-provider support (OpenRouter/OpenAI, Ollama)

## Prerequisites

- Java 25+
- Gradle
- API key for OpenRouter or OpenAI (default: OpenRouter)

## Getting Started

1. Set your API key:
   ```bash
   export OPENROUTER_API_KEY=your_api_key_here
   ```

2. Run the application:
   ```bash
   ./gradlew bootRun
   ```

## API Usage

Send a POST request to the chat endpoint:

```bash
curl -X POST http://localhost:8080/api/support/chat \
  -H "Content-Type: text/plain" \
  -d "What is the status of order 123?"
```

The AI agent can:
- Look up order details by ID
- Process refunds for delivered orders

## Configuration

Edit `src/main/resources/application.properties` to switch AI providers:

- **OpenRouter** (default): Uses GPT-4o-mini via OpenRouter
- **OpenAI**: Uncomment the OpenAI section and set `OPENAI_API_KEY`
- **Ollama**: Uncomment the Ollama section for local LLM usage (requires Ollama running locally)

## Project Structure

- `AgentConfig.java` - AI agent setup with system prompt and tools
- `OrderTools.java` - Function definitions for the AI (get order, process refund)
- `SupportController.java` - REST endpoint for chat
- `OrderService.java` - Business logic for orders and refunds
