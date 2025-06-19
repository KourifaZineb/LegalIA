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

    private Long userId;
    private Long lawyerId;

    @Enumerated(EnumType.STRING)
    private ConnectionStatus status;

    private String caseDescription;
    private String objet;

    private Instant requestDate;
}
