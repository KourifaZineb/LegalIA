package com.chatbot.userservice.services;

import com.chatbot.userservice.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> getUsersByStatus(String status);
    User updateUser(User user);
    void deleteUser(Long id);
    boolean authenticateUser(String email, String password);
    void changeUserLanguage(Long userId, String language);
}
