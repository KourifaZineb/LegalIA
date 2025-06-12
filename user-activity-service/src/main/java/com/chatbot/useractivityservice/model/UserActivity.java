package com.chatbot.useractivityservice.entities;

import com.chatbot.useractivityservice.entities.enums.ActivityType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_activities")
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;
    @Enumerated(EnumType.STRING)
    private ActivityType activityType;
    private LocalDateTime timestamp;
    private String details;

}
