package com.chatbot.userservice.entities;

import com.chatbot.userservice.entities.enums.Role;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "admin")
    private Set<SystemMetrics> systemMetrics = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    private Set<Lawyer> monitoredLawyers = new HashSet<>();

}

