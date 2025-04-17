package com.chatbot.userservice.entities;

import com.chatbot.userservice.entities.enums.Language;
import com.chatbot.userservice.entities.enums.Speciality;
import com.chatbot.userservice.entities.enums.lawyerStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lawyers")
public class Lawyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lawyerId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private Language languages;
    @Enumerated(EnumType.STRING)
    private lawyerStatus status;
    private Speciality specialization;
    private double hourlyRate;
    private double rating;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    @ManyToOne
    private Admin admin;

    @OneToMany(mappedBy = "lawyer")
    private Set<Notifications> notifications = new HashSet<>();

}

