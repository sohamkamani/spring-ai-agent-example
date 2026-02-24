package com.example.support.model;

import java.util.List;

public record AgentResponse(String content, List<String> toolsCalled) {
}
