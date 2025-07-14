package com.chatbot.commonlibrary.dtos.chat;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonAlias("session_id")
    private String sessionId;
    @NotNull
    private String userId;
    @NotNull
    private String question;
    private List<MultipartFile> attachments;
}