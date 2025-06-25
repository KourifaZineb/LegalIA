package com.chatbotservice.services;

import com.chatbot.commonlibrary.dtos.chat.ChatRequest;
import com.chatbot.commonlibrary.dtos.chat.ChatResponse;

public interface ChatService {
    /**
     * Traite un message utilisateur dans une session de chat donnée
     * @param request contenant sessionId (conversationId) et message utilisateur
     * @return ChatResponse avec le même sessionId et la réponse du bot
     */
    ChatResponse processMessage(ChatRequest request);
}
