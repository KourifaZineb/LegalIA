package com.chatbot.documentservice.service.Impl;

import com.chatbot.commonlibrary.dtos.DocumentDto;
import com.chatbot.documentservice.model.ConversationDocument;
import com.chatbot.documentservice.repository.ConversationDocumentRepository;
import com.chatbot.documentservice.service.DocumentService;
import com.chatbotservice.model.Conversations;
import com.chatbotservice.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final ConversationDocumentRepository documentRepository;

    public DocumentServiceImpl(ConversationDocumentRepository documentRepository) {
        this.documentRepository    = documentRepository;
    }

    @Override
    @Transactional
    public ConversationDocument saveDocument(DocumentDto request) {
        try {
            String base64 = request.getFileContent();
            byte[] fileBytes = Base64.getDecoder().decode(base64);
            String fileId = UUID.randomUUID().toString();
            String fileName = fileId + "_" + request.getFileName();
            System.out.println("Received Base64: " + request.getFileContent());

            // Sauvegarde dans le disque
            String filePath = Paths.get("uploads", fileName).toString();
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(fileBytes);
            }

            ConversationDocument document = ConversationDocument.builder()
                    .documentId(fileId)
                    .conversationId(request.getConversationId())
                    .fileName(request.getFileName())
                    .fileType(request.getFileType())
                    .filePath(filePath)
                    .uploadDate(LocalDateTime.now())
                    .extractedText(request.getFileContent())
                    .build();

            return documentRepository.save(document);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'enregistrement du document", e);
        }
    }
}
