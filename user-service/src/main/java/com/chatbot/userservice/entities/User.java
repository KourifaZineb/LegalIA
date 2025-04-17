package com.chatbot.userservice.entities;

import com.chatbot.userservice.entities.enums.Language;
import com.chatbot.userservice.entities.enums.userStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private userStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "PREFERRED_LANGUAGE")
    private Language preferredLanguage;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Notifications> notifications = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<SystemMetrics> metrics = new HashSet<>();

}
