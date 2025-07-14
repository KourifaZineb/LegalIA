package com.chatbot.commonlibrary.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationsDTO {
    private String conversationId;
    private String userId;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String language;
    private boolean isActive;

    private List<MessagesDTO> messages;
}