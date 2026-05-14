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
public class DocumentServiceClient {

    private final RestTemplate restTemplate;
    private final String documentServiceUrl;

    public DocumentServiceClient(RestTemplate restTemplate,
                                 @Value("${document.service.url}") String documentServiceUrl) {
        this.restTemplate = restTemplate;
        this.documentServiceUrl = documentServiceUrl;
    }

    /**
     * Upload document to document service
     */
    public Map<String, Object> uploadDocument(MultipartFile file, String conversationId, String customFileName) {
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
            body.add("conversationId", conversationId);
            if (customFileName != null) {
                body.add("name", customFileName);
            }

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            String url = documentServiceUrl + "/api/documents/upload";
            ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

            log.info("Document uploaded successfully: {}", response.getBody());
            return (Map<String, Object>) response.getBody();

        } catch (Exception e) {
            log.error("Error uploading document to document service", e);
            throw new RuntimeException("Failed to upload document: " + e.getMessage());
        }
    }

    /**
     * Get documents by conversation ID
     */
    public Object getDocumentsByConversation(String conversationId) {
        try {
            String url = documentServiceUrl + "/api/documents/conversation/" + conversationId;
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error retrieving documents for conversation: {}", conversationId, e);
            throw new RuntimeException("Failed to retrieve documents: " + e.getMessage());
        }
    }

    /**
     * Get download URL for document
     */
    public String getDownloadUrl(String documentId) {
        try {
            String url = documentServiceUrl + "/api/documents/" + documentId + "/url";
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            Map<String, Object> responseBody = response.getBody();
            return (String) responseBody.get("downloadUrl");
        } catch (Exception e) {
            log.error("Error getting download URL for document: {}", documentId, e);
            throw new RuntimeException("Failed to get download URL: " + e.getMessage());
        }
    }

    /**
     * Delete document
     */
    public void deleteDocument(String documentId) {
        try {
            String url = documentServiceUrl + "/api/documents/" + documentId;
            restTemplate.delete(url);
            log.info("Document deleted successfully: {}", documentId);
        } catch (Exception e) {
            log.error("Error deleting document: {}", documentId, e);
            throw new RuntimeException("Failed to delete document: " + e.getMessage());
        }
    }
}