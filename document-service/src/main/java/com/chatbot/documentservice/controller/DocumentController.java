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
@RequestMapping("/api/conversations/{conversationId}/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final ConversationDocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public DocumentController(DocumentService documentService,
                              ConversationDocumentRepository documentRepository,
                              DocumentMapper documentMapper) {
        this.documentService     = documentService;
        this.documentRepository  = documentRepository;
        this.documentMapper      = documentMapper;
    }

    /**
     * Upload d'un document lié à une conversation.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentDto> upload(
            @PathVariable String conversationId,
            @RequestPart("file") MultipartFile file) {

        ConversationDocument saved = documentService.saveDocument(conversationId, file);
        DocumentDto dto = documentMapper.toDto(saved);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dto);
    }

    /**
     * Liste les documents attachés à une conversation.
     */
    @GetMapping
    public ResponseEntity<List<DocumentDto>> listByConversation(
            @PathVariable String conversationId) {

        List<ConversationDocument> docs = documentRepository
                .findByConversationId(conversationId);

        List<DocumentDto> dtos = docs.stream()
                .map(documentMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
