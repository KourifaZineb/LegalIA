package com.chatbot.commonlibrary.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessagesDTO {
    private String messageId;
    private String conversationId;
    private String content;
    private LocalDateTime timestamp;
    private boolean userMessage;
    private Float confidenceScore;
    private List<String> entities;
    private String intent;
}