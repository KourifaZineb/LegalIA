package com.chatbot.documentservice.repository;

import com.chatbot.commonlibrary.enums.DocumentStatus;
import com.chatbot.documentservice.model.ConversationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
// Remove this line: import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationDocumentRepository extends JpaRepository<ConversationDocument, String> {
    /**
     * Récupère tous les documents d'une conversation
     */
    List<ConversationDocument> findByConversationId(String conversationId);
    List<ConversationDocument> findByConversationIdAndStatus(String conversationId, DocumentStatus status);
    Optional<ConversationDocument> findByDocumentIdAndStatus(String documentId, DocumentStatus status);
}