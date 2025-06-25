package com.chatbotservice.model;

import com.chatbot.userservice.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conversations {

    @Id
    @Column(name = "conversation_id", nullable = false, updatable = false, length = 255)
    private String conversationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "startTime", nullable = false)
    private LocalDateTime startTime = LocalDateTime.now();

    @Column(name = "endTime")
    private LocalDateTime endTime;

    @Column(name = "language", length = 10)
    private String language;

    @Column(name = "isActive", nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Messages> messages = new ArrayList<>();
}