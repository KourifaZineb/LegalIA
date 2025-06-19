package com.chatbot.userservice.services.servicesImpl;

import com.chatbot.commonlibrary.dtos.UserDTO;
import com.chatbot.commonlibrary.exception.NotFoundException;
import com.chatbot.userservice.mapper.UserMapper;
import com.chatbot.userservice.model.User;
import com.chatbot.userservice.repository.UserRepository;
import com.chatbot.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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
        Instant now = Instant.now();
        user.setCreatedAt(now);
        user.setLastLogin(now);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (dto.getName() != null) {
            existing.setName(dto.getName());
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
        if (dto.getSolde() != null) {
            existing.setSolde(dto.getSolde());
        }
        return mapper.toDto(repository.save(existing));
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
