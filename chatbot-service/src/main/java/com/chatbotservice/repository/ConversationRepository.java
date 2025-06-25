package com.chatbotservice.repository;

import com.chatbotservice.model.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversations, String> {

    /**
     * Récupère toutes les conversations d'un utilisateur donné.
     */
    List<Conversations> findByUserUserId(Integer userId);

    /**
     * Récupère la conversation active (isActive = true) pour un utilisateur.
     */
    List<Conversations> findByUserUserIdAndIsActiveTrue(Integer userId);
}
