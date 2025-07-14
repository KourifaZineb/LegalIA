package com.chatbotservice.services;

import com.chatbot.commonlibrary.dtos.ConversationsDTO;
import com.chatbot.commonlibrary.dtos.MessagesDTO;
import com.chatbot.commonlibrary.dtos.chat.ChatRequest;
import com.chatbot.commonlibrary.dtos.chat.ChatResponse;
import com.chatbotservice.model.Conversations;

import java.util.List;
import java.util.UUID;

public interface ChatService {
    /**
     * Traite un message utilisateur dans une session de chat donnée
     * @param request contenant sessionId (conversationId) et message utilisateur
     * @return ChatResponse avec le même sessionId et la réponse du bot
     */
    ChatResponse processMessage(ChatRequest request);
    List<Conversations> getAllConversations();
    List<ConversationsDTO> getConversationsByUserId(String userId);
    List<MessagesDTO> getMessagesByConversationId(String conversationId);
}
