package com.chatbot.documentservice.service;

import com.chatbot.commonlibrary.dtos.DocumentDto;
import com.chatbot.documentservice.model.ConversationDocument;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    ConversationDocument saveDocument(DocumentDto request);
}