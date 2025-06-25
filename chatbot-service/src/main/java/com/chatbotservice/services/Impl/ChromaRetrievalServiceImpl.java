package com.chatbotservice.services.impl;

import com.chatbotservice.services.ChromaRetrievalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ChromaRetrievalServiceImpl implements ChromaRetrievalService {

    private final RestTemplate restTemplate;
    private final String retrieveUrl;

    public ChromaRetrievalServiceImpl(RestTemplate restTemplate,
                                      @Value("${chroma.api.url}") String retrieveUrl) {
        this.restTemplate = restTemplate;
        this.retrieveUrl  = retrieveUrl;
    }

    @Override
    public List<String> getContext(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,String> body = Map.of("query", query);
        HttpEntity<Map<String,String>> req = new HttpEntity<>(body, headers);

        ResponseEntity<Map> resp = restTemplate.exchange(
                retrieveUrl,
                HttpMethod.POST,
                req,
                Map.class
        );

        @SuppressWarnings("unchecked")
        List<String> context = (List<String>) resp.getBody().get("context");
        return context;
    }
}
