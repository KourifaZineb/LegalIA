package com.chatbot.userservice.entities;

import com.chatbot.userservice.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "admin")
    private Set<SystemMetrics> systemMetrics = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    private Set<Lawyer> monitoredLawyers = new HashSet<>();

}
