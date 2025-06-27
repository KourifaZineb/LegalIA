package com.chatbotservice.services.Impl;

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
    public String infer(String sessionId, String question) {
        // On ne met que ce que Flask attend : sessionId + question
        Map<String, Object> payload = new HashMap<>();
        payload.put("sessionId", sessionId);
        payload.put("question",  question);

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.postForObject(apiUrl, payload, Map.class);

        // Adapté à la clé que votre API renvoie : "answer" ou "reply"
        return response.get("answer").toString();
    }
}
