package com.chatbot.documentservice.service;

import com.chatbot.documentservice.model.ConversationDocument;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    /**
     * Sauvegarde un fichier pour une conversation donnée et retourne l'entité correspondant.
     * @param conversationId Identifiant de la conversation
     * @param file           Fichier uploadé par l'utilisateur
     */
    ConversationDocument saveDocument(String conversationId, MultipartFile file);
}