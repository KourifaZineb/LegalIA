package com.chatbot.documentservice.service.Impl;

import com.chatbot.documentservice.model.ConversationDocument;
import com.chatbot.documentservice.repository.ConversationDocumentRepository;
import com.chatbot.documentservice.service.DocumentService;
import com.chatbotservice.model.Conversations;
import com.chatbotservice.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final ConversationDocumentRepository documentRepository;
    private final ConversationRepository         conversationRepository;

    public DocumentServiceImpl(ConversationDocumentRepository documentRepository,
                               ConversationRepository conversationRepository) {
        this.documentRepository    = documentRepository;
        this.conversationRepository = conversationRepository;
    }

    @Override
    @Transactional
    public ConversationDocument saveDocument(String conversationId, MultipartFile file) {
        // 1. Charger la conversation (entité Conversation, pas Conversations)
        Conversations conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Conversation not found: " + conversationId)
                );

        // 2. Préparer les meta
        String documentId   = UUID.randomUUID().toString();
        String originalName = file.getOriginalFilename();
        String contentType  = file.getContentType();

        // 3. Créer le dossier uploads/{conversationId}
        Path uploadDir = Paths.get("uploads", conversationId);
        try {
            Files.createDirectories(uploadDir);
            String storedName = documentId + "_" + originalName;
            Path target = uploadDir.resolve(storedName);
            file.transferTo(target.toFile());

            // 4. Extraction de texte (optionnelle) – à implémenter
            String extractedText = "";

            // 5. Construire et persister l’entité ConversationDocument
            ConversationDocument doc = ConversationDocument.builder()
                    .documentId   (documentId)
                    .conversationId (conversationId)
                    .fileName     (originalName)
                    .fileType     (contentType)
                    .filePath     (target.toString())
                    .uploadDate   (LocalDateTime.now())
                    .extractedText(extractedText)
                    .build();

            return documentRepository.save(doc);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + originalName, e);
        }
    }
}
