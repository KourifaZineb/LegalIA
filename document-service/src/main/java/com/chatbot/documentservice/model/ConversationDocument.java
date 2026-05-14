package com.chatbot.documentservice.model;

import com.chatbot.commonlibrary.enums.DocumentStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conversation_documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationDocument {

    @Id
    @Column(name = "document_id")
    private String documentId;

    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @Column(name = "firebase_storage_path", nullable = false)
    private String firebaseStoragePath;

    @Column(name = "firebase_bucket")
    private String firebaseBucket;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DocumentStatus status;

    @Column(name = "extracted_text", columnDefinition = "TEXT")
    private String extractedText;
}