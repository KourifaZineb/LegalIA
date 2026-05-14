package com.chatbot.documentservice.controller;
import com.chatbot.commonlibrary.dtos.DocumentDto;
import com.chatbot.documentservice.mapper.DocumentMapper;
import com.chatbot.documentservice.model.ConversationDocument;
import com.chatbot.documentservice.repository.ConversationDocumentRepository;
import com.chatbot.documentservice.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(
        origins = "http://localhost:4200",
        allowedHeaders = {"Authorization", "Content-Type", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
        allowCredentials = "true",
        maxAge = 3600
)
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a document")
    public ResponseEntity<Map<String, Object>> uploadDocument(
            @Parameter(description = "File to upload", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Conversation ID", required = true)
            @RequestParam("conversationId") String conversationId,
            @Parameter(description = "Custom file name")
            @RequestParam(value = "name", required = false) String customFileName) {

        try {
            log.info("Starting file upload for conversation: {}", conversationId);

            // Upload document
            ConversationDocument savedDocument = documentService.saveDocument(file, conversationId, customFileName);

            // Return success response
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "File uploaded successfully");
            response.put("documentId", savedDocument.getDocumentId());
            response.put("fileName", savedDocument.getFileName());
            response.put("fileSize", savedDocument.getFileSize());
            response.put("mimeType", savedDocument.getMimeType());
            response.put("uploadDate", savedDocument.getUploadDate());
            response.put("status", savedDocument.getStatus());

            log.info("File upload completed successfully for document: {}", savedDocument.getDocumentId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error uploading document for conversation: {}", conversationId, e);

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error uploading document: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping
    @Operation(summary = "Get all documents regardless of status")
    public ResponseEntity<Object> getAllDocuments() {
        try {
            log.info("Retrieving all documents regardless of status");
            List<ConversationDocument> documents = documentService.getAllDocuments();

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("documents", documents);
            response.put("totalCount", documents.size());

            log.info("Retrieved {} documents successfully", documents.size());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error retrieving all documents", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving documents: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{documentId}")
    @Operation(summary = "Get document metadata by ID")
    public ResponseEntity<Object> getDocument(@PathVariable String documentId) {
        try {
            Optional<ConversationDocument> document = documentService.getDocumentById(documentId);
            if (document.isPresent()) {
                return ResponseEntity.ok(document.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error retrieving document: {}", documentId, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving document");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{documentId}/download")
    @Operation(summary = "Download document file")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable String documentId) {
        try {
            Optional<ConversationDocument> documentOpt = documentService.getDocumentById(documentId);
            if (!documentOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            ConversationDocument document = documentOpt.get();
            byte[] fileContent = documentService.downloadDocument(documentId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(document.getMimeType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + document.getFileName() + "\"")
                    .body(fileContent);

        } catch (Exception e) {
            log.error("Error downloading document: {}", documentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{documentId}/url")
    @Operation(summary = "Get Firebase download URL")
    public ResponseEntity<Map<String, Object>> getDownloadUrl(@PathVariable String documentId) {
        try {
            String downloadUrl = documentService.getFirebaseDownloadUrl(documentId);
            Map<String, Object> response = new HashMap<>();
            response.put("downloadUrl", downloadUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error generating download URL: {}", documentId, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error generating download URL");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/conversation/{conversationId}")
    @Operation(summary = "Get documents by conversation ID")
    public ResponseEntity<Object> getDocumentsByConversation(@PathVariable String conversationId) {
        try {
            List<ConversationDocument> documents = documentService.getDocumentsByConversationId(conversationId);
            return ResponseEntity.ok(documents);
        } catch (Exception e) {
            log.error("Error retrieving documents for conversation: {}", conversationId, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error retrieving documents");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{documentId}")
    @Operation(summary = "Delete document")
    public ResponseEntity<Map<String, Object>> deleteDocument(@PathVariable String documentId) {
        try {
            documentService.deleteDocument(documentId);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Document deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error deleting document: {}", documentId, e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Error deleting document: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
