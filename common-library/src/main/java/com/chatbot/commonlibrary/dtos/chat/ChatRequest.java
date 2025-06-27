package com.chatbot.commonlibrary.dtos.chat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequest {
    @NotNull
    private String sessionId;
    @NotNull
    private Long userId;
    @NotNull
    private String message;
    private List<MultipartFile> attachments;
}