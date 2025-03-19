package com.chatbot.userservice.services;

import com.chatbot.userservice.dtos.UserDTO;
import com.chatbot.userservice.entities.User;
import com.chatbot.userservice.enums.Language;
import com.chatbot.userservice.enums.userStatus;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    Optional<UserDTO> getUserById(Long id);
    List<UserDTO> getAllUsers();
    List<UserDTO> getUsersByStatus(userStatus status);
    UserDTO updateUser(UserDTO userDTO);
    void deleteUser(Long id);
    void changeUserLanguage(Long userId, Language language);
    Optional<UserDTO> getUserByEmail(String email);
    boolean authenticateUser(String email, String password);
    List<UserDTO> searchByPreferredLanguage(Language language);
    boolean existsByEmail(String email);
}
