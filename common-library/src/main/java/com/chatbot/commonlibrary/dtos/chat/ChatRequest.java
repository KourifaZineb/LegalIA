package com.chatbot.commonlibrary.dtos.chat;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     * Optional attachments for document analysis
     * This field is populated by the controller when files are uploaded
     */
    @JsonIgnore // Don't serialize this field in JSON
    private List<MultipartFile> attachments;

    /**
     * Optional flag to indicate if document analysis is requested
     */
    private Boolean analyzeDocuments = false;

    /**
     * Optional analysis type for documents (e.g., "summary", "extract", "comprehensive")
     */
    private String analysisType = "comprehensive";
}