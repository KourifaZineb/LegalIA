package com.chatbot.documentservice.repository;

import com.chatbot.documentservice.model.ConversationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationDocumentRepository extends MongoRepository<ConversationDocument, String> {
    /**
     * Récupère tous les documents d'une conversation
     */
    //List<ConversationDocument> findByConversationId(String conversationId);
}