package com.chatbotservice.model;

import org.hibernate.annotations.Type;
import com.vladmihalcea.hibernate.type.array.StringArrayType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

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

    @Type(value = StringArrayType.class)
    @Column(name = "keywords", columnDefinition = "text[]")
    private String[] keywords;

    @Column(name = "contentText", columnDefinition = "TEXT")
    private String contentText;

    @Column(name = "language", length = 10)
    private String language;
}
