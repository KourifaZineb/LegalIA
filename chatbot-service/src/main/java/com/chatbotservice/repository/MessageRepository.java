package com.chatbotservice.repository;

import com.chatbotservice.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Messages, String> {

    /**
     * Récupère tous les messages d'une conversation triés par date.
     */
    List<Messages> findByConversationConversationIdOrderByTimestampAsc(String conversationId);
    List<Messages> findByConversation_ConversationId(String conversationId);
}