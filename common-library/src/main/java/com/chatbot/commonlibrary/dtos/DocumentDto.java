package com.chatbot.commonlibrary.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class DocumentDto {
    /** Identifiant du document (UUID) */
    private String documentId;

    /** Identifiant de la conversation associée */
    private String conversationId;

    /** Nom original du fichier */
    private String fileName;

    /** Type MIME du fichier */
    private String fileType;

    /** Chemin (ou URL) où le fichier est stocké */
    private String filePath;

    /** Date & heure d’upload */
    private LocalDateTime uploadDate;

    /** Texte extrait du document (pour la RAG) */
    private String extractedText;
}
