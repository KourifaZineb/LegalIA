package com.chatbot.commonlibrary.dtos;

import com.chatbot.commonlibrary.enums.NotificationType;
import lombok.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private String title;
    private String message;
    private String fcmToken;
    private String type;
    private Long connectionId;
    private String userId;
    private Long lawyerId;

    private UserDTO userDTO;
}