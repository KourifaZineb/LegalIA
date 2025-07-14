package com.chatbotservice.controllers;

import com.chatbot.commonlibrary.dtos.ConversationsDTO;
import com.chatbot.commonlibrary.dtos.MessagesDTO;
import com.chatbot.commonlibrary.dtos.chat.ChatRequest;
import com.chatbot.commonlibrary.dtos.chat.ChatResponse;
import com.chatbotservice.model.Conversations;
import com.chatbotservice.services.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@Validated
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /*@PostMapping
    public ResponseEntity<ChatResponse> chat(@Validated @RequestBody ChatRequest request) {
        ChatResponse response = chatService.processMessage(request);
        return ResponseEntity.ok(response);
    }*/

    @GetMapping("/conversations")
    public ResponseEntity<List<Conversations>> getAllConversations() {
        List<Conversations> conversations = chatService.getAllConversations();
        return ResponseEntity.ok(conversations);
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ChatResponse> chat(
            @RequestPart("chat") ChatRequest request,
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments
    ) {
        request.setAttachments(attachments); // injecter les fichiers dans l'objet
        ChatResponse response = chatService.processMessage(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ConversationsDTO>> getConversationsByUserId(@PathVariable String userId) {
        List<ConversationsDTO> conversations = chatService.getConversationsByUserId(userId);
        return ResponseEntity.ok(conversations);
    }

    @GetMapping("/conversation/{conversationId}")
    public ResponseEntity<List<MessagesDTO>> getMessagesByConversationId(@PathVariable String conversationId) {
        List<MessagesDTO> conversations = chatService.getMessagesByConversationId(conversationId);
        return ResponseEntity.ok(conversations);
    }

}