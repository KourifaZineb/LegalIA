package com.chatbot.commonlibrary.dtos;

import com.chatbot.commonlibrary.enums.ActivityType;
import lombok.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserActivityDTO {
    private Long id;
    private ActivityType activityType;
    private Long userId;
    private String details;
    private Instant timestamp;

    private UserDTO userDTO;
}
