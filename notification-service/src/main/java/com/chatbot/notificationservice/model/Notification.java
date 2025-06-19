package com.chatbot.notificationservice.model;

import com.chatbot.commonlibrary.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "lawyer_id", nullable = false)
    private Long lawyerId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private Instant timestamp = Instant.now();

    @Column(name = "is_read", nullable = false)
    private boolean read;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
}
