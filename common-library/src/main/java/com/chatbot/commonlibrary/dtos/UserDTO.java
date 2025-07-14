package com.chatbot.commonlibrary.dtos;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbot.commonlibrary.enums.Role;
import com.chatbot.commonlibrary.enums.UserStatus;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    //private String password; // ✅ Ce champ est obligatoire !
    private String phoneNumber;
    private Language preferredLanguage;
    private UserStatus status;
    private Role role;
    private Instant createdAt;
    private Instant lastLogin;
}