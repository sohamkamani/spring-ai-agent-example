package com.example.support.controller;

import com.example.support.service.CustomerSupportAgent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    private final CustomerSupportAgent customerSupportAgent;

    public SupportController(CustomerSupportAgent customerSupportAgent) {
        this.customerSupportAgent = customerSupportAgent;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        return customerSupportAgent.chat(message);
    }
}
