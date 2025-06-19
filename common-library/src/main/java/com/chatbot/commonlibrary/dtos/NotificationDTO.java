package com.chatbot.commonlibrary.dtos;

import com.chatbot.commonlibrary.enums.NotificationType;
import lombok.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationDTO {
    private Long id;
    private Long userId;
    private Long lawyerId;
    private String content;
    private Instant timestamp;
    private boolean read;
    private NotificationType notificationType;
    private UserDTO userDTO;
}