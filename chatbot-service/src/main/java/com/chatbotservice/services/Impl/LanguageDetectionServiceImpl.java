
package com.chatbotservice.services.Impl;

import com.chatbot.commonlibrary.util.ValidationUtil;
import com.chatbotservice.services.LanguageDetectionService;
import org.springframework.stereotype.Service;

@Service
public class LanguageDetectionServiceImpl implements LanguageDetectionService {

    @Override
    public String detect(String code) {
        // Utilisez votre utilitaire ou une librairie externe
        return ValidationUtil.isLanguageCodeValid(code) ? code : "fr";
    }
}
