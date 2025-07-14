package com.chatbot.userservice.repository;

import com.chatbot.userservice.model.User;
import io.swagger.v3.oas.models.media.UUIDSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    User     findUserById(UUID id);

    // ← add this:
    Optional<User> findByKeycloakId(String keycloakId);
}
