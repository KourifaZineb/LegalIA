package com.chatbot.documentservice.controller;
import com.chatbot.commonlibrary.dtos.DocumentDto;
import com.chatbot.documentservice.mapper.DocumentMapper;
import com.chatbot.documentservice.model.ConversationDocument;
import com.chatbot.documentservice.repository.ConversationDocumentRepository;
import com.chatbot.documentservice.service.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<ConversationDocument> uploadDocument(@RequestBody DocumentDto request) {
        ConversationDocument document = documentService.saveDocument(request);
        return ResponseEntity.ok(document);
    }
}
