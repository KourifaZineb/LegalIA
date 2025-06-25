package com.chatbot.commonlibrary.dtos.chat;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequest {
    @NotBlank
    private String sessionId;

    @NotBlank
    private String message;
    private List<MultipartFile> attachments;
}