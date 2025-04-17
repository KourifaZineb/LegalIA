package com.chatbot.userservice.services.servicesImpl;

import com.chatbot.userservice.dtos.UserDTO;
import com.chatbot.userservice.entities.User;
import com.chatbot.userservice.entities.enums.Language;
import com.chatbot.userservice.entities.enums.userStatus;
import com.chatbot.userservice.mappers.UserMapper;
import com.chatbot.userservice.repository.UserRepository;
import com.chatbot.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = userMapper.convertToEntity(userDTO);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        if (user.getStatus() == null) {
            user.setStatus(userStatus.ACTIF);
        }
        if (user.getPreferredLanguage() == null) {
            user.setPreferredLanguage(Language.FRANÇAIS);
        }

        // Sauvegarder l'entité
        User savedUser = userRepository.save(user);

        // Conversion de l'entité sauvegardée en DTO
        return userMapper.convertToDTO(savedUser);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::convertToDTO);
    }
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUsersByStatus(userStatus status) {
        return userRepository.findByStatus(status).stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        Optional<User> existingUserOpt = userRepository.findById(userDTO.getUserId());

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            LocalDateTime originalCreatedAt = existingUser.getCreatedAt();

            // Mise à jour partielle de l'entité existante
            userMapper.updateEntityFromDTO(userDTO, existingUser);
            existingUser.setLastLogin(LocalDateTime.now());
            existingUser.setCreatedAt(originalCreatedAt);
            // Gestion spéciale du mot de passe
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }

            // Sauvegarder les modifications
            User updateUser = userRepository.save(existingUser);

            return userMapper.convertToDTO(updateUser);
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void changeUserLanguage(Long userId, Language language) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPreferredLanguage(language);
            userRepository.save(user);
        }
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::convertToDTO);
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            boolean matches = passwordEncoder.matches(password, user.getPassword());
            if (matches) {
                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UserDTO> searchByPreferredLanguage(Language language) {
        List<User> users = userRepository.findByPreferredLanguage(language);
        return users.stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


}
