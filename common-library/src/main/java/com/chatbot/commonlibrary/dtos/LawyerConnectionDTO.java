package com.chatbot.commonlibrary.dtos;

import com.chatbot.commonlibrary.enums.ConnectionStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LawyerConnectionDTO {
    private Long connectionId;
    private Long userId;
    private Long lawyerId;
    private Instant requestDate;
    private ConnectionStatus status;
    private String caseDescription;
    private String objet;

    private UserDTO user;       // ✅ pour enrichissement
    private LawyerDTO lawyer;   // ✅ pour enrichissement
}
