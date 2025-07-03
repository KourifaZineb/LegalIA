package com.chatbotservice.services;

import com.chatbot.commonlibrary.enums.Language;

public interface LanguageDetectionService {
    /**
     * Détecte la langue ISO (ex. "fr", "ar", "en") du texte donné.
     */
    Language detect(String text);
}