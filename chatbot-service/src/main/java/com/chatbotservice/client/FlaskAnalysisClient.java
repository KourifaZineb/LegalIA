package com.chatbotservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Component
@Slf4j
public class FlaskAnalysisClient {

    private final RestTemplate restTemplate;
    private final String flaskApiUrl;

    public FlaskAnalysisClient(RestTemplate restTemplate,
                               @Value("${flask.api.url}") String flaskApiUrl) {
        this.restTemplate = restTemplate;
        this.flaskApiUrl = flaskApiUrl;
    }

    /**
     * Send document to Flask API for analysis
     */
    public Map<String, Object> analyzeDocument(MultipartFile file, String analysisType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file", new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            });

            if (analysisType != null) {
                body.add("analysis_type", analysisType);
            }

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            String url = flaskApiUrl + "/analyze"; // Adjust endpoint as needed
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            log.info("Document analysis completed successfully");
            return (Map<String, Object>) response.getBody();

        } catch (Exception e) {
            log.error("Error analyzing document with Flask API", e);
            throw new RuntimeException("Failed to analyze document: " + e.getMessage());
        }
    }

    /**
     * Send document URL to Flask API for analysis (if Flask can download from URL)
     */
    public Map<String, Object> analyzeDocumentByUrl(String documentUrl, String analysisType) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = Map.of(
                    "document_url", documentUrl,
                    "analysis_type", analysisType != null ? analysisType : "default"
            );

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            String url = flaskApiUrl + "/analyze-url"; // Adjust endpoint as needed
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            log.info("Document analysis by URL completed successfully");
            return (Map<String, Object>) response.getBody();

        } catch (Exception e) {
            log.error("Error analyzing document by URL with Flask API", e);
            throw new RuntimeException("Failed to analyze document by URL: " + e.getMessage());
        }
    }
}