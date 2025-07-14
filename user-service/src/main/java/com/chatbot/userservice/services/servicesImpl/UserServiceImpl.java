/*
package com.chatbot.userservice.services.servicesImpl;

import com.chatbot.commonlibrary.dtos.UserDTO;
import com.chatbot.commonlibrary.enums.Role;
import com.chatbot.commonlibrary.exception.NotFoundException;
import com.chatbot.userservice.mapper.UserMapper;
import com.chatbot.userservice.model.User;
import com.chatbot.userservice.repository.UserRepository;
import com.chatbot.userservice.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO dto) {
        // Encode le mot de passe si présent
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            System.out.println("debut de if");
            System.out.println("Mot de passe avant encodage : " + dto.getPassword());
            dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        } else {
            System.out.println("debut de else");
            throw new IllegalArgumentException("Le mot de passe ne peut pas être null ou vide");
        }
        System.out.println("hors de if");
        System.out.println("Mot de passe après encodage : " + dto.getPassword());
        User user = mapper.toEntity(dto);
        dto.setRole(Role.USER);
        Instant now = Instant.now();
        user.setCreatedAt(now);
        user.setLastLogin(now);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public UserDTO getUserById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO dto) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (dto.getFirstName() != null) {
            existing.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            existing.setFirstName(dto.getLastName());
        }
        if (dto.getPhoneNumber() != null) {
            existing.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getPreferredLanguage() != null) {
            existing.setPreferredLanguage(dto.getPreferredLanguage());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getRole() != null) {
            existing.setRole(dto.getRole());
        }
        return mapper.toDto(repository.save(existing));
    }

    @Override
    public void deleteUser(UUID id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void provisionUser(String keycloakId,
                              String email,
                              String firstName,
                              String lastName) {
        log.info("Provisioning user with Keycloak ID {}", keycloakId);
        // upsert by Keycloak ID
        User user = repository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    User u = new User();
                    u.setKeycloakId(keycloakId);
                    u.setRole(Role.USER);                // default role
                    u.setCreatedAt(Instant.now());       // first‐seen timestamp
                    return u;
                });
        log.info("User with Keycloak ID {} exists: {}", keycloakId);
        // update profile fields
        user.setEmail(email);
        user.setFirstName(firstName);                  // or setName(...)
        user.setLastName(lastName);
        user.setLastLogin(Instant.now());
        log.info("Updated user profile: {}", user);
        repository.save(user);
    }
}
*/
package com.chatbot.userservice.services.servicesImpl;

import com.chatbot.commonlibrary.dtos.UserDTO;
import com.chatbot.commonlibrary.enums.Role;
import com.chatbot.commonlibrary.enums.UserStatus;
import com.chatbot.commonlibrary.exception.NotFoundException;
import com.chatbot.userservice.mapper.UserMapper;
import com.chatbot.userservice.model.User;
import com.chatbot.userservice.repository.UserRepository;
import com.chatbot.userservice.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository   repository;
    private final UserMapper       mapper;
    private final PasswordEncoder  passwordEncoder;

    @Override
    public UserDTO createUser(UserDTO dto) {
        /*if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas être null ou vide");
        }*/

        // On fixe le rôle dès le départ
        dto.setRole(Role.USER);
        // On encode le mot de passe
        //dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Mapping DTO → entité
        User user = mapper.toEntity(dto);
        Instant now = Instant.now();
        user.setCreatedAt(now);
        user.setLastLogin(now);
        user.setKeycloakId(user.getKeycloakId());

        // Persistance
        User saved = repository.save(user);

        // Mapping entité → DTO (inclut le hash du mot de passe)
        return mapper.toDto(saved);
    }

    @Override
    public UserDTO getUserById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public UserDTO updateUser(UUID id, UserDTO dto) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (dto.getFirstName() != null) {
            existing.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            existing.setLastName(dto.getLastName());
        }
        if (dto.getPhoneNumber() != null) {
            existing.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getPreferredLanguage() != null) {
            existing.setPreferredLanguage(dto.getPreferredLanguage());
        }
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }
        if (dto.getRole() != null) {
            existing.setRole(dto.getRole());
        }
        // Si on souhaite autoriser la mise à jour du mot de passe via ce DTO :
        /*if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }*/

        User updated = repository.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public void deleteUser(UUID id) {
        repository.deleteById(id);
    }

    /*@Override
    @Transactional
    public void provisionUser(String keycloakId,
                              String email,
                              String firstName,
                              String lastName) {
        log.info("Provisioning user with Keycloak ID {}", keycloakId);

        User user = repository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {
                    User u = new User();
                    u.setKeycloakId(keycloakId);
                    u.setRole(Role.USER);
                    u.setStatus(UserStatus.ACTIVE);
                    // mot de passe factice pour satisfaire la contrainte NOT NULL
                    String dummy = UUID.randomUUID().toString();
                    //u.setPassword(passwordEncoder.encode(dummy));
                    u.setCreatedAt(Instant.now());
                    return u;
                });

        // Mise à jour des champs
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLastLogin(Instant.now());
        // Optionnel : rafraîchir la date de création à chaque appel
        user.setCreatedAt(Instant.now());

        repository.save(user);
        log.debug("User provisioned: {}", user.getId());
    }*/
}
