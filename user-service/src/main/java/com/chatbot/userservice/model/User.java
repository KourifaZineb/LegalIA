package com.chatbot.userservice.model;

import com.chatbot.commonlibrary.enums.Language;
import com.chatbot.commonlibrary.enums.Role;
import com.chatbot.commonlibrary.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(unique=true)
    private String keycloakId;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    //@Column(nullable = false)  //✅ Très important !
    /*private String password;*/

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Language preferredLanguage;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Instant createdAt;
    private Instant lastLogin;
}
