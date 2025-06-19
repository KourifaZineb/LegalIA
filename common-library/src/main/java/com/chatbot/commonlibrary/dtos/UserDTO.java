package com.chatbot.commonlibrary.dtos;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbot.commonlibrary.enums.UserStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password; // ✅ Ce champ est obligatoire !
    private String phoneNumber;
    private Language preferredLanguage;
    private UserStatus status;
    private Double solde;
    private Instant createdAt;
    private Instant lastLogin;
}