package com.chatbot.commonlibrary.dtos;

import com.chatbot.commonlibrary.enums.ConnectionStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LawyerConnectionDTO {
    private Long id;
    private String userId;
    private Long lawyerId;
    private ConnectionStatus status;
    private String caseDescription;
    private String objet;
    private Instant requestDate;
    private Instant responseDate;
    private String message; // Optional message from user
    private UserDTO userInfo;
    private LawyerDTO lawyerInfo;
}
