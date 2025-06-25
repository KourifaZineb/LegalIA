package com.chatbot.documentservice.model;

import com.chatbotservice.model.Conversations;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "conversation_documents")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ConversationDocument {

    @Id
    private String documentId;

    /** Référence à l’ID de la conversation stockée (Postgres ou autre) */
    private String conversationId;

    private String fileName;
    private String fileType;
    private String filePath;
    private LocalDateTime uploadDate;
    private String extractedText;
}