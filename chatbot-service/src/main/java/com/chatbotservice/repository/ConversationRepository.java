package com.chatbotservice.repository;

import com.chatbotservice.model.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversations, String> {

    List<Conversations> findByUserId(String userId);
}
