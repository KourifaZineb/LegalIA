package com.chatbot.commonlibrary.dtos;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbot.commonlibrary.enums.LawyerStatus;
import com.chatbot.commonlibrary.enums.Role;
import com.chatbot.commonlibrary.enums.Specialization;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class LawyerDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String adresse;
    private Specialization specialization;
    private Language languages;
    private Role role;
    private LawyerStatus status;
    private Instant createdAt;
    private Instant lastLogin;
}
