package com.chatbot.commonlibrary.dtos.responses;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ErrorResponse {

    private String returnCode;       // ex: "404", "500", "001"
    private String returnMessage;    // ex: "Utilisateur introuvable"
    private LocalDateTime timestamp;

    public ErrorResponse(String returnCode, String returnMessage) {
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
        this.timestamp = LocalDateTime.now();
    }

    // Getters et setters
}
