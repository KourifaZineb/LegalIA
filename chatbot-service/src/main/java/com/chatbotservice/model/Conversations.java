package com.chatbotservice.model;

import com.chatbot.commonlibrary.enums.Language;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Conversations {

    @Id
    @Column(name = "conversation_id", nullable = false, updatable = false, length = 255)
    private String conversationId;

    /** Plus de ManyToOne, juste l’ID de l’utilisateur */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime = LocalDateTime.now();

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "language", length = 10)
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<Messages> messages = new ArrayList<>();
}
