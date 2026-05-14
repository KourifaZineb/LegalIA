package com.chatbot.lawyerconnectorservice.model;

import com.chatbot.commonlibrary.enums.ConnectionStatus;
import com.chatbot.lawyerservice.model.Lawyer;
import com.chatbot.userservice.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "lawyer_connections")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 36) // UUID is 36 characters with hyphens
    private String userId;

    @Column(nullable = false)
    private Long lawyerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConnectionStatus status;

    @Column(columnDefinition = "TEXT")
    private String caseDescription;

    private String objet;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private Instant requestDate;

    private Instant responseDate;

    @PrePersist
    public void prePersist() {
        if (requestDate == null) {
            requestDate = Instant.now();
        }
        if (status == null) {
            status = ConnectionStatus.PENDING;
        }
    }
}
