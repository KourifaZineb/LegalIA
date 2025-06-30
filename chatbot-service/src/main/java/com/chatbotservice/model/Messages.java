package com.chatbotservice.model;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Messages {

    @Id
    @Column(name = "message_id", nullable = false, updatable = false, length = 255)
    private String messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Conversations conversation;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "isUserMessage", nullable = false)
    private boolean userMessage;
/*
    @Column(name = "confidenceScore")
    private Float confidenceScore;

    @Column(name = "intent", length = 100)
    private String intent;*/
}