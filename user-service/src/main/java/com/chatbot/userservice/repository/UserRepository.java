package com.chatbot.userservice.repository;

import com.chatbot.commonlibrary.enums.UserStatus;
import com.chatbot.userservice.model.User;
import com.chatbot.commonlibrary.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
