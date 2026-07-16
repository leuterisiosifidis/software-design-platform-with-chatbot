package com.myy803.project.controllers;

import com.myy803.project.domain.ChatRequest;
import com.myy803.project.domain.ChatResponse;
import com.myy803.project.services.OpenAIService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final OpenAIService openAIService;

    public ChatController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ChatResponse chat(@RequestBody ChatRequest request) {
        String userMessage = request.getMessage();

        if (userMessage == null || userMessage.trim().isEmpty()) {
            return new ChatResponse("Please type a message first.");
        }

        String reply = openAIService.ask(userMessage.trim(), request.getCurrentPage());
        return new ChatResponse(reply);
    }
}
