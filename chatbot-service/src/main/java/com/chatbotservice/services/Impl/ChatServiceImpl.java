package com.chatbotservice.services.Impl;

import com.chatbot.commonlibrary.dtos.ConversationsDTO;
import com.chatbot.commonlibrary.dtos.MessagesDTO;
import com.chatbot.commonlibrary.dtos.chat.ChatRequest;
import com.chatbot.commonlibrary.dtos.chat.ChatResponse;
import com.chatbot.commonlibrary.enums.Language;
import com.chatbotservice.client.DocumentServiceClient;
import com.chatbotservice.client.FlaskAnalysisClient;
import com.chatbotservice.mapper.ConversationMapper;
import com.chatbotservice.mapper.MessageMapper;
import com.chatbotservice.model.Conversations;
import com.chatbotservice.model.Messages;
import com.chatbotservice.repository.ConversationRepository;
import com.chatbotservice.repository.MessageRepository;
import com.chatbotservice.services.ChatService;
import com.chatbotservice.services.LanguageDetectionService;
import com.chatbotservice.services.ModelInferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final LanguageDetectionService languageDetectionService;
    private final ModelInferenceService modelInferenceService;
    private final ConversationMapper conversationMapper;
    private final MessageMapper messageMapper;
    private final DocumentServiceClient documentServiceClient;
    private final FlaskAnalysisClient flaskAnalysisClient;

    public ChatServiceImpl(ConversationRepository conversationRepository,
                           MessageRepository messageRepository,
                           LanguageDetectionService languageDetectionService,
                           ModelInferenceService modelInferenceService,
                           ConversationMapper conversationMapper,
                           MessageMapper messageMapper,
                           DocumentServiceClient documentServiceClient,
                           FlaskAnalysisClient flaskAnalysisClient) {
        this.conversationRepository   = conversationRepository;
        this.messageRepository        = messageRepository;
        this.languageDetectionService = languageDetectionService;
        this.modelInferenceService    = modelInferenceService;
        this.conversationMapper = conversationMapper;
        this.messageMapper = messageMapper;
        this.documentServiceClient = documentServiceClient;
        this.flaskAnalysisClient = flaskAnalysisClient;
    }

    @Override
    @Transactional
    public ChatResponse processMessage(ChatRequest request) {
        try {
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

            // 3. Sauvegarder le message de l'utilisateur
            Messages userMsg = Messages.builder()
                    .messageId(UUID.randomUUID().toString())
                    .conversation(conversation)
                    .content(request.getQuestion())
                    .timestamp(LocalDateTime.now())
                    .userMessage(true)
                    .build();
            messageRepository.save(userMsg);

            // 4. Traiter les documents attachés s'ils existent
            String documentAnalysisResult = null;
            if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
                documentAnalysisResult = processDocumentAttachments(request.getAttachments(), request.getSessionId());
                log.info("Document analysis completed for conversation: {}", request.getSessionId());
            }

            // 5. Préparer le contexte pour le modèle NLP (inclut l'analyse de documents si disponible)
            String enhancedQuestion = buildEnhancedQuestion(request.getQuestion(), documentAnalysisResult);

            // 6. Appeler le modèle NLP
            ChatResponse modelResponse = modelInferenceService.infer(
                    request.getSessionId(),
                    enhancedQuestion
            );
            String reply = modelResponse.getAnswer();
            String title = modelResponse.getTitle();

            // 7. Sauvegarder la réponse du bot
            Messages botMsg = Messages.builder()
                    .messageId(UUID.randomUUID().toString())
                    .conversation(conversation)
                    .content(reply)
                    .timestamp(LocalDateTime.now())
                    .userMessage(false)
                    .build();
            messageRepository.save(botMsg);

            // 8. Mettre à jour la conversation
            conversation.setEndTime(LocalDateTime.now());
            conversation.setActive(true);
            conversation.setLanguage(language);
            if (conversation.getTitle() == null || conversation.getTitle().isEmpty()) {
                conversation.setTitle(title);
            }
            conversationRepository.save(conversation);

            // 9. Retourner la réponse
            return ChatResponse.builder()
                    .answer(reply)
                    .title(title)
                    .build();

        } catch (Exception e) {
            log.error("Error processing chat message for session: {}", request.getSessionId(), e);
            return ChatResponse.builder()
                    .answer("Désolé, une erreur s'est produite lors du traitement de votre message.")
                    .title("Erreur")
                    .build();
        }
    }

    /**
     * Process document attachments: upload to document service and analyze with Flask API
     */
    private String processDocumentAttachments(List<MultipartFile> attachments, String conversationId) {
        StringBuilder analysisResults = new StringBuilder();

        for (MultipartFile attachment : attachments) {
            try {
                log.info("Processing attachment: {} for conversation: {}",
                        attachment.getOriginalFilename(), conversationId);

                // 1. Upload document to document service
                Map<String, Object> uploadResult = documentServiceClient.uploadDocument(
                        attachment, conversationId, null);

                if (uploadResult != null && (Boolean) uploadResult.get("success")) {
                    String documentId = (String) uploadResult.get("documentId");
                    log.info("Document uploaded successfully with ID: {}", documentId);

                    // 2. Analyze document with Flask API
                    Map<String, Object> analysisResult = flaskAnalysisClient.analyzeDocument(
                            attachment, "comprehensive"); // or whatever analysis type you need

                    if (analysisResult != null) {
                        // Extract analysis content (adjust based on your Flask API response structure)
                        String analysis = extractAnalysisContent(analysisResult);
                        analysisResults.append("Document: ").append(attachment.getOriginalFilename())
                                .append("\nAnalysis: ").append(analysis).append("\n\n");
                    }
                } else {
                    log.warn("Failed to upload document: {}", attachment.getOriginalFilename());
                }

            } catch (Exception e) {
                log.error("Error processing attachment: {}", attachment.getOriginalFilename(), e);
                analysisResults.append("Erreur lors du traitement du document: ")
                        .append(attachment.getOriginalFilename()).append("\n");
            }
        }

        return analysisResults.toString();
    }

    /**
     * Extract analysis content from Flask API response
     */
    private String extractAnalysisContent(Map<String, Object> analysisResult) {
        // Adjust this method based on your Flask API response structure
        if (analysisResult.containsKey("analysis")) {
            return (String) analysisResult.get("analysis");
        } else if (analysisResult.containsKey("summary")) {
            return (String) analysisResult.get("summary");
        } else if (analysisResult.containsKey("content")) {
            return (String) analysisResult.get("content");
        }
        return "Analyse disponible mais format non reconnu.";
    }

    /**
     * Build enhanced question with document analysis context
     */
    private String buildEnhancedQuestion(String originalQuestion, String documentAnalysis) {
        if (documentAnalysis == null || documentAnalysis.trim().isEmpty()) {
            return originalQuestion;
        }

        return String.format("""
            Question de l'utilisateur: %s
            
            Contexte des documents analysés:
            %s
            
            Veuillez répondre en tenant compte du contexte fourni par les documents.
            """, originalQuestion, documentAnalysis);
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

    @Override
    public List<MessagesDTO> getMessagesByConversationId(String conversationId) {
        List<Messages> list = messageRepository.findByConversation_ConversationId(conversationId);
        log.debug("Retrieved {} messages for conversation: {}", list.size(), conversationId);
        return list.stream().map(messageMapper::toDto).collect(Collectors.toList());
    }
}