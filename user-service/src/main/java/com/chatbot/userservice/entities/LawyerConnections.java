package com.chatbot.userservice.entities;

import com.chatbot.userservice.enums.ConnectionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lawyer_connections")
public class LawyerConnections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long connectionId;
    private LocalDateTime requestDate;
    private ConnectionStatus status;
    private String caseDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lawyer_id")
    private Lawyer lawyer;
}
