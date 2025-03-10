package com.chatbot.userservice.repository;

import com.chatbot.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    List<User> findByStatus(String status);
    List<User> findByPreferredLanguage(String language);
    boolean existsByEmail(String email);
}
