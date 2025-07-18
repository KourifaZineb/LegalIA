package com.chatbotservice.services.Impl;

import com.chatbot.commonlibrary.dtos.ConversationsDTO;
import com.chatbot.commonlibrary.dtos.MessagesDTO;
import com.chatbot.commonlibrary.dtos.chat.ChatRequest;
import com.chatbot.commonlibrary.dtos.chat.ChatResponse;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbotservice.mapper.ConversationMapper;
import com.chatbotservice.mapper.MessageMapper;
import com.chatbotservice.model.Conversations;
import com.chatbotservice.model.Messages;
import com.chatbotservice.repository.ConversationRepository;
import com.chatbotservice.repository.MessageRepository;
import com.chatbotservice.services.ChatService;
import com.chatbotservice.services.LanguageDetectionService;
import com.chatbotservice.services.ModelInferenceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final LanguageDetectionService languageDetectionService;
    private final ModelInferenceService modelInferenceService;
    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;

    public ChatServiceImpl(ConversationRepository conversationRepository,
                           MessageRepository messageRepository,
                           LanguageDetectionService languageDetectionService,
                           ModelInferenceService modelInferenceService, ConversationMapper conversationMapper, MessageMapper messageMapper) {
        this.conversationRepository   = conversationRepository;
        this.messageRepository        = messageRepository;
        this.languageDetectionService = languageDetectionService;
        this.modelInferenceService    = modelInferenceService;
        this.conversationMapper = conversationMapper;
        this.messageMapper = messageMapper;
    }

    @Override
    @Transactional
    public ChatResponse processMessage(ChatRequest request) {
        // 1. Détection de la langue
        Language language = languageDetectionService.detect(request.getQuestion());

        // 2. Récupérer ou créer une conversation
        Conversations conversation = conversationRepository
                .findById(request.getSessionId())
                .orElseGet(() -> {
                    Conversations conv = Conversations.builder()
                            .conversationId(request.getSessionId())
                            .userId(request.getUserId())
                            .startTime(LocalDateTime.now())
                            .isActive(true)
                            .build();
                    return conversationRepository.save(conv);
                });

        // 3. Sauvegarder le message de l’utilisateur
        Messages userMsg = Messages.builder()
                .messageId(UUID.randomUUID().toString())
                .conversation(conversation)
                .content(request.getQuestion())
                .timestamp(LocalDateTime.now())
                .userMessage(true)
                .build();
        messageRepository.save(userMsg);

        // 4. Appeler le modèle NLP
        ChatResponse modelResponse = modelInferenceService.infer(
                request.getSessionId(),
                request.getQuestion()
        );
        String reply = modelResponse.getAnswer();
        String title = modelResponse.getTitle();

        // 5. Sauvegarder la réponse du bot
        Messages botMsg = Messages.builder()
                .messageId(UUID.randomUUID().toString())
                .conversation(conversation)
                .content(reply)
                .timestamp(LocalDateTime.now())
                .userMessage(false)
                .build();
        messageRepository.save(botMsg);

        // 6. Mettre à jour la conversation
        conversation.setEndTime(LocalDateTime.now());
        conversation.setActive(true);
        conversation.setLanguage(language);
        if (conversation.getTitle() == null || conversation.getTitle().isEmpty()) {
            conversation.setTitle(title); // Mettre à jour seulement si vide
        }
        conversationRepository.save(conversation);

        // 7. Retourner la réponse
        return ChatResponse.builder()
                .answer(reply)
                .title(title)
                .build();
    }

    @Override
    public List<Conversations> getAllConversations() {
        return conversationRepository.findAll();
    }

    @Override
    public List<ConversationsDTO> getConversationsByUserId(String userId) {
        List<Conversations> list = conversationRepository.findByUserId(userId);
        return list.stream()
                .map(conversationMapper::toDto)
                .collect(Collectors.toList());
    }

    // ChatServiceImpl.java
    @Override
    public List<MessagesDTO> getMessagesByConversationId(String conversationId) {
        List<Messages> list = messageRepository.findByConversation_ConversationId(conversationId);
        System.out.println("la liste des messages est : " + list + "--");
        return list.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }


}
