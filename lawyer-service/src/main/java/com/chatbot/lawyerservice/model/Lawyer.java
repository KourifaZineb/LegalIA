package com.chatbot.lawyerservice.model;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbot.commonlibrary.enums.LawyerStatus;
import com.chatbot.commonlibrary.enums.Specialization;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "lawyers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lawyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String adresse;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    private Double solde;

    @CollectionTable(name = "lawyer_languages", joinColumns = @JoinColumn(name = "lawyer_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language languages;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LawyerStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "last_login")
    private Instant lastLogin;
}
