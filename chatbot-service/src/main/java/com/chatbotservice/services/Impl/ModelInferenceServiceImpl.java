package com.chatbotservice.services.Impl;

import com.chatbot.commonlibrary.dtos.chat.ChatResponse;
import com.chatbotservice.services.ModelInferenceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModelInferenceServiceImpl implements ModelInferenceService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public ModelInferenceServiceImpl(RestTemplate restTemplate,
                                     @Value("${nlp.python.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl       = apiUrl;
    }

    @Override
    public ChatResponse infer(String sessionId, String question) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("sessionId", sessionId);
        payload.put("question", question);

        return restTemplate.postForObject(apiUrl, payload, ChatResponse.class);
    }
}
