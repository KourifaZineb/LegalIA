package com.chatbotservice.services.Impl;

import com.chatbot.commonlibrary.dtos.chat.ChatRequest;
import com.chatbot.commonlibrary.dtos.chat.ChatResponse;
import com.chatbotservice.model.Conversations;
import com.chatbotservice.model.Messages;
import com.chatbotservice.repository.ConversationRepository;
import com.chatbotservice.repository.MessageRepository;
import com.chatbotservice.services.ChatService;
import com.chatbotservice.services.ChromaRetrievalService;
import com.chatbotservice.services.LanguageDetectionService;
import com.chatbotservice.services.ModelInferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final LanguageDetectionService languageDetectionService;
    private final ChromaRetrievalService chromaService;
    private final ModelInferenceService modelInferenceService;

    public ChatServiceImpl(ConversationRepository conversationRepository,
                           MessageRepository messageRepository,
                           LanguageDetectionService languageDetectionService,
                           ChromaRetrievalService chromaService,
                           ModelInferenceService modelInferenceService) {
        this.conversationRepository   = conversationRepository;
        this.messageRepository        = messageRepository;
        this.languageDetectionService = languageDetectionService;
        this.chromaService       = chromaService;
        this.modelInferenceService    = modelInferenceService;
    }

    @Override
    @Transactional
    public ChatResponse processMessage(ChatRequest request) {
        // 1. Récupérer ou créer la conversation
        Conversations conversation = conversationRepository
                .findById(request.getSessionId())
                .orElseGet(() -> {
                    Conversations conv = Conversations.builder()
                            .conversationId(request.getSessionId())
                            .startTime(LocalDateTime.now())
                            .isActive(true)
                            .build();
                    return conversationRepository.save(conv);
                });

        // 2. Sauvegarder le message de l’utilisateur
        Messages userMsg = Messages.builder()
                .messageId(UUID.randomUUID().toString())
                .conversation(conversation)
                .content(request.getMessage())
                .timestamp(LocalDateTime.now())
                .userMessage(true)
                .build();
        messageRepository.save(userMsg);

        // 3. Détection de la langue
        String language = languageDetectionService.detect(request.getMessage());

        // 4. Récupérer le contexte légal (RAG)
        List<String> context = chromaService.getContext(request.getMessage());

        // 5. Générer la réponse via inference
        String reply = modelInferenceService.infer(request.getMessage(), context, language);

        // 6. Sauvegarder la réponse du bot
        Messages botMsg = Messages.builder()
                .messageId(UUID.randomUUID().toString())
                .conversation(conversation)
                .content(reply)
                .timestamp(LocalDateTime.now())
                .userMessage(false)
                .build();
        messageRepository.save(botMsg);

        // 7. Mettre à jour la fin et l’état de la conversation
        conversation.setEndTime(LocalDateTime.now());
        conversation.setActive(true);
        conversationRepository.save(conversation);

        // 8. Retourner la réponse
        return ChatResponse.builder()
                .sessionId(request.getSessionId())
                .reply(reply)
                .build();
    }
}
