package com.chatbotservice.controllers;

import com.chatbot.commonlibrary.dtos.chat.ChatRequest;
import com.chatbot.commonlibrary.dtos.chat.ChatResponse;
import com.chatbotservice.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@Validated
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Envoie un message au chatbot et récupère la réponse.
     *
     * @param request contient sessionId et message de l'utilisateur
     * @return ChatResponse avec sessionId et reply du bot
     */
    @PostMapping
    public ResponseEntity<ChatResponse> chat(@Validated @RequestBody ChatRequest request) {
        ChatResponse response = chatService.processMessage(request);
        return ResponseEntity.ok(response);
    }
}