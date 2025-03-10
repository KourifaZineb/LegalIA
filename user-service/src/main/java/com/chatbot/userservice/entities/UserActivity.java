package com.chatbot.userservice.entities;

import com.chatbot.userservice.enums.ActivityType;
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
    private ActivityType activityType;
    private LocalDateTime timestamp;
    private String details;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
