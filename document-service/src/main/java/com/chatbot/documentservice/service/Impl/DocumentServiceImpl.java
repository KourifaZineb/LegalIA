package com.chatbot.documentservice.service.Impl;

import com.chatbot.commonlibrary.dtos.DocumentDto;
import com.chatbot.commonlibrary.enums.DocumentStatus;
import com.chatbot.documentservice.model.ConversationDocument;
import com.chatbot.documentservice.repository.ConversationDocumentRepository;
import com.chatbot.documentservice.service.DocumentService;
import com.chatbot.documentservice.service.FirebaseStorageService;
import com.chatbotservice.model.Conversations;
import com.chatbotservice.repository.ConversationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final ConversationDocumentRepository documentRepository;
    private final FirebaseStorageService firebaseStorageService;

    public DocumentServiceImpl(ConversationDocumentRepository documentRepository,
                               FirebaseStorageService firebaseStorageService) {
        this.documentRepository = documentRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Override
    public List<ConversationDocument> getAllDocuments() {
        log.info("Retrieving all documents regardless of status");
        return documentRepository.findAll();
    }

    @Override
    public ConversationDocument saveDocument(MultipartFile file, String conversationId, String customFileName) {
        String documentId = UUID.randomUUID().toString();

        try {
            // Validate file
            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            String fileName = (customFileName != null && !customFileName.isEmpty())
                    ? customFileName : file.getOriginalFilename();

            // Determine MIME type
            String mimeType = file.getContentType();
            if (mimeType == null || mimeType.isEmpty()) {
                mimeType = determineMimeType(fileName);
            }

            log.info("Starting upload for file: {} ({})", fileName, mimeType);

            // 1. First, save document metadata in PostgreSQL with UPLOADING status
            ConversationDocument document = ConversationDocument.builder()
                    .documentId(documentId)
                    .conversationId(conversationId)
                    .fileName(fileName)
                    .fileType(determineFileType(fileName))
                    .mimeType(mimeType)
                    .fileSize(file.getSize())
                    .uploadDate(LocalDateTime.now())
                    .firebaseStoragePath(null) // Will be set after Firebase upload
                    .firebaseBucket(firebaseStorageService.getBucketName())
                    .status(DocumentStatus.UPLOADING)
                    .extractedText(null)
                    .build();

            // Save to PostgreSQL first
            ConversationDocument savedDocument = documentRepository.save(document);
            log.info("Document metadata saved to PostgreSQL with ID: {}", documentId);

            try {
                // 2. Upload file to Firebase Storage
                String firebaseStoragePath = firebaseStorageService.uploadDocument(file, conversationId, documentId);
                log.info("File uploaded to Firebase at path: {}", firebaseStoragePath);

                // 3. Update document with Firebase path and PROCESSED status
                savedDocument.setFirebaseStoragePath(firebaseStoragePath);
                savedDocument.setStatus(DocumentStatus.PROCESSED);
                savedDocument = documentRepository.save(savedDocument);

                log.info("Document upload completed successfully: {}", documentId);
                return savedDocument;

            } catch (Exception firebaseException) {
                log.error("Firebase upload failed for document: {}", documentId, firebaseException);

                // Update status to FAILED in database
                savedDocument.setStatus(DocumentStatus.FAILED);
                documentRepository.save(savedDocument);

                throw new RuntimeException("Failed to upload file to Firebase: " + firebaseException.getMessage());
            }

        } catch (Exception e) {
            log.error("Error during document upload for ID: {}", documentId, e);
            throw new RuntimeException("Error uploading document: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ConversationDocument> getDocumentById(String documentId) {
        return documentRepository.findByDocumentIdAndStatus(documentId, DocumentStatus.PROCESSED);
    }

    @Override
    public List<ConversationDocument> getDocumentsByConversationId(String conversationId) {
        return documentRepository.findByConversationIdAndStatus(conversationId, DocumentStatus.PROCESSED);
    }

    @Override
    public void deleteDocument(String documentId) {
        Optional<ConversationDocument> documentOpt = documentRepository.findById(documentId);
        if (documentOpt.isPresent()) {
            ConversationDocument document = documentOpt.get();

            try {
                // Delete from Firebase if path exists
                if (document.getFirebaseStoragePath() != null) {
                    firebaseStorageService.deleteDocument(document.getFirebaseStoragePath());
                }
            } catch (Exception e) {
                log.error("Error deleting from Firebase, continuing with database deletion", e);
            }

            // Delete from PostgreSQL
            documentRepository.deleteById(documentId);
            log.info("Document deleted: {}", documentId);
        } else {
            throw new RuntimeException("Document not found: " + documentId);
        }
    }

    @Override
    public byte[] downloadDocument(String documentId) {
        ConversationDocument document = documentRepository.findByDocumentIdAndStatus(documentId, DocumentStatus.PROCESSED)
                .orElseThrow(() -> new RuntimeException("Document not found or not processed: " + documentId));

        if (document.getFirebaseStoragePath() == null) {
            throw new RuntimeException("Firebase storage path not found for document: " + documentId);
        }

        return firebaseStorageService.downloadDocument(document.getFirebaseStoragePath());
    }

    @Override
    public String getFirebaseDownloadUrl(String documentId) {
        ConversationDocument document = documentRepository.findByDocumentIdAndStatus(documentId, DocumentStatus.PROCESSED)
                .orElseThrow(() -> new RuntimeException("Document not found or not processed: " + documentId));

        if (document.getFirebaseStoragePath() == null) {
            throw new RuntimeException("Firebase storage path not found for document: " + documentId);
        }

        return firebaseStorageService.getDownloadUrl(document.getFirebaseStoragePath());
    }

    private String determineMimeType(String fileName) {
        if (fileName == null) return "application/octet-stream";

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "pdf": return "application/pdf";
            case "doc": return "application/msword";
            case "docx": return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "txt": return "text/plain";
            case "jpg": case "jpeg": return "image/jpeg";
            case "png": return "image/png";
            case "xlsx": return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "xls": return "application/vnd.ms-excel";
            default: return "application/octet-stream";
        }
    }

    private String determineFileType(String fileName) {
        if (fileName == null) return "UNKNOWN";

        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "pdf": return "PDF";
            case "doc": case "docx": return "WORD";
            case "txt": return "TEXT";
            case "jpg": case "jpeg": case "png": return "IMAGE";
            case "xlsx": case "xls": return "EXCEL";
            default: return "OTHER";
        }
    }
}
