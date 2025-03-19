package com.chatbot.userservice.repository;

import com.chatbot.userservice.entities.User;
import com.chatbot.userservice.enums.Language;
import com.chatbot.userservice.enums.userStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByStatus(userStatus status);
    Optional<User> findByEmail(String email);

    List<User> findByPreferredLanguage(Language language);
    boolean existsByEmail(String email);
}
