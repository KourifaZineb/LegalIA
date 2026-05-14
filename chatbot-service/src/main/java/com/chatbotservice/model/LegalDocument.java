package com.chatbotservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "legal_documents")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LegalDocument {

    @Id
    @Column(name = "document_id", length = 255, nullable = false, updatable = false)
    private String documentId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "documentType", length = 100)
    private String documentType;

    @Column(name = "uploadDate", nullable = false)
    private LocalDateTime uploadDate = LocalDateTime.now();

    @Column(name = "filePath", length = 500)
    private String filePath;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "contentText", columnDefinition = "TEXT")
    private String contentText;

    @Column(name = "language", length = 10)
    private String language;
}