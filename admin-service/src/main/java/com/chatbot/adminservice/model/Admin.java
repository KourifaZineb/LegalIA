package com.chatbot.adminservice.model;

import com.chatbot.commonlibrary.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "admins")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long adminId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private Instant lastLogin;

    private Instant createdAt;

}
