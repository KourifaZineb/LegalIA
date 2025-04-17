package com.chatbot.userservice.dtos.request;

import com.chatbot.userservice.entities.enums.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogActivityRequest {
    private ActivityType activityType;
    private String details;
}
