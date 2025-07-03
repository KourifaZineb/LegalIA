
package com.chatbotservice.services.Impl;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbotservice.services.LanguageDetectionService;
import org.springframework.stereotype.Service;

@Service
public class LanguageDetectionServiceImpl implements LanguageDetectionService {

    @Override
    public Language detect(String text) {
        if (text == null || text.isBlank()) {
            return Language.français; // Valeur par défaut
        }

        // Détection basique de caractères arabes
        boolean containsArabic = text.codePoints().anyMatch(
                c -> (c >= 0x0600 && c <= 0x06FF) || (c >= 0x0750 && c <= 0x077F)
        );

        return containsArabic ? Language.arabe : Language.français;
    }
}
