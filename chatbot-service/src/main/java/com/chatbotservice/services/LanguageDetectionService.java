package com.chatbotservice.services;

public interface LanguageDetectionService {
    /**
     * Détecte la langue ISO (ex. "fr", "ar", "en") du texte donné.
     */
    String detect(String text);
}