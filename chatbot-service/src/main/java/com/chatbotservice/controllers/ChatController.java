package com.chatbotservice.controllers;

import com.chatbot.commonlibrary.dtos.ConversationsDTO;
import com.chatbot.commonlibrary.dtos.MessagesDTO;
import com.chatbot.commonlibrary.dtos.chat.ChatRequest;
import com.chatbot.commonlibrary.dtos.chat.ChatResponse;
import com.chatbotservice.model.Conversations;
import com.chatbotservice.services.ChatService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@Validated
@Slf4j
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Send chat message with optional document attachments")
    public ResponseEntity<ChatResponse> chat(
            @Parameter(description = "Chat request containing message and session info")
            @RequestPart("chat") String chatJson, // Change to String
            @Parameter(description = "Optional document attachments for analysis")
            @RequestPart(value = "attachments", required = false) List<MultipartFile> attachments
    ) {
        try {
            // Manually deserialize the JSON string to ChatRequest
            ObjectMapper objectMapper = new ObjectMapper();
            ChatRequest request = objectMapper.readValue(chatJson, ChatRequest.class);

            log.info("Processing chat request for session: {} with {} attachments",
                    request.getSessionId(),
                    attachments != null ? attachments.size() : 0);

            // Set attachments in the request
            request.setAttachments(attachments);

            // Process the message (including document analysis if attachments exist)
            ChatResponse response = chatService.processMessage(request);

            log.info("Chat request processed successfully for session: {}", request.getSessionId());
            return ResponseEntity.ok(response);

        } catch (JsonProcessingException e) {
            log.error("Error parsing chat JSON: {}", chatJson, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ChatResponse.builder()
                            .answer("Format JSON invalide pour le chat: " + e.getMessage())
                            .title("Erreur")
                            .build());
        } catch (Exception e) {
            log.error("Error processing chat request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ChatResponse.builder()
                            .answer("Désolé, une erreur système s'est produite: " + e.getMessage())
                            .title("Erreur")
                            .build());
        }
    }

    @PostMapping(value = "/text", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Send text-only chat message")
    public ResponseEntity<ChatResponse> chatTextOnly(@Valid @RequestBody ChatRequest request) {
        try {
            log.info("Processing text-only chat request for session: {}", request.getSessionId());

            ChatResponse response = chatService.processMessage(request);

            log.info("Text-only chat request processed successfully for session: {}", request.getSessionId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error processing text-only chat request for session: {}", request.getSessionId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ChatResponse.builder()
                            .answer("Désolé, une erreur système s'est produite: " + e.getMessage())
                            .title("Erreur")
                            .build());
        }
    }

    @GetMapping("/conversations")
    @Operation(summary = "Get all conversations")
    public ResponseEntity<List<Conversations>> getAllConversations() {
        try {
            List<Conversations> conversations = chatService.getAllConversations();
            return ResponseEntity.ok(conversations);
        } catch (Exception e) {
            log.error("Error retrieving all conversations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get conversations by user ID")
    public ResponseEntity<List<ConversationsDTO>> getConversationsByUserId(
            @Parameter(description = "User ID to filter conversations")
            @PathVariable String userId) {
        try {
            List<ConversationsDTO> conversations = chatService.getConversationsByUserId(userId);
            return ResponseEntity.ok(conversations);
        } catch (Exception e) {
            log.error("Error retrieving conversations for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/conversation/{conversationId}")
    @Operation(summary = "Get messages by conversation ID")
    public ResponseEntity<List<MessagesDTO>> getMessagesByConversationId(
            @Parameter(description = "Conversation ID to retrieve messages")
            @PathVariable String conversationId) {
        try {
            List<MessagesDTO> messages = chatService.getMessagesByConversationId(conversationId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            log.error("Error retrieving messages for conversation: {}", conversationId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}