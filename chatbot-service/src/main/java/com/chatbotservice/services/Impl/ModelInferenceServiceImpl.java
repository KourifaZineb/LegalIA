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
    public String infer(String message, List<String> context, String language) {
        // Prépare le payload JSON
        Map<String,Object> payload = new HashMap<>();
        payload.put("question", message);
        payload.put("context",  context);
        payload.put("language", language);

        // Appel POST vers votre API Flask
        @SuppressWarnings("unchecked")
        Map<String,Object> response = restTemplate.postForObject(apiUrl, payload, Map.class);

        // Récupère la clé "answer" ou adaptez si votre API renvoie "reply"
        return response.get("answer").toString();
    }
}
