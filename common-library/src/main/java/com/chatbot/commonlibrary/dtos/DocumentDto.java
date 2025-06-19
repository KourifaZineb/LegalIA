package com.chatbot.commonlibrary.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class DocumentDto {
    private Long documentId;
    private Long userId;
    private String title;
    private String documentType;
    private String status;
    private String[] keywords;
    private String language;
    private LocalDateTime uploadDate;
}
