package com.chatbot.commonlibrary.dtos;


import com.chatbot.commonlibrary.enums.Role;
import lombok.*;
import java.time.Instant;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AdminDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Role role;
    private Instant lastLogin;
    private Instant createdAt;
}
