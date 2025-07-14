package com.chatbotservice.services.Impl;

import com.chatbot.commonlibrary.dtos.chat.ChatResponse;
import com.chatbotservice.services.ModelInferenceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModelInferenceServiceImpl implements ModelInferenceService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public ModelInferenceServiceImpl(RestTemplate restTemplate,
                                     @Value("${nlp.python.api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl;
    }

    @Override
    public ChatResponse infer(String session_id, String question) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("sessionId", session_id);
        payload.put("question", question);

        // LOG pour debug
        System.out.println(">>> Payload envoyé au modèle Python : " + payload);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<ChatResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                request,
                ChatResponse.class
        );

        return response.getBody();
    }
}
