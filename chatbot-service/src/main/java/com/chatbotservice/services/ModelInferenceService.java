package com.chatbotservice.services;

import java.util.List;

public interface ModelInferenceService {
    /**
     * Appelle l’API externe pour générer la réponse.
     * @param message  texte utilisateur
     * @param context  contexte juridique
     * @param language code langue ISO
     */
    String infer(String sessionId, String question);
}
