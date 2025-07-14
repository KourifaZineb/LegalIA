package com.chatbot.commonlibrary.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class DocumentDto {
    private String conversationId;
    private String fileName;
    private String fileType;
    private String fileContent;
}
