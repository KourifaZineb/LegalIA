package com.chatbot.documentservice.service;

import com.chatbot.commonlibrary.dtos.DocumentDto;
import com.chatbot.documentservice.model.ConversationDocument;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    ConversationDocument saveDocument(MultipartFile file, String conversationId, String customFileName);
    Optional<ConversationDocument> getDocumentById(String documentId);
    List<ConversationDocument> getDocumentsByConversationId(String conversationId);
    void deleteDocument(String documentId);
    byte[] downloadDocument(String documentId);
    String getFirebaseDownloadUrl(String documentId);
    List<ConversationDocument> getAllDocuments();
}