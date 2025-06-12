package com.chatbot.lawyerservice.entities;

import com.chatbot.lawyerservice.entities.enums.Language;
import com.chatbot.lawyerservice.entities.enums.LawyerStatus;
import com.ged.lawyerservice.entities.enums.Speciality;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LawyerStatus status;
    private Speciality specialization;
    private double hourlyRate;
    private double rating;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

/*    @ManyToOne
    private Admin admin;*/

}

