package com.chatbot.adminservice.service.serviceImpl;


import com.chatbot.adminservice.mapper.AdminMapper;
import com.chatbot.adminservice.model.Admin;
import com.chatbot.adminservice.repository.AdminRepository;
import com.chatbot.adminservice.service.AdminService;
import com.chatbot.commonlibrary.dtos.AdminDTO;
import com.chatbot.commonlibrary.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repository;
    private final AdminMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository repository, AdminMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AdminDTO createAdmin(AdminDTO dto) {
       // Encode le mot de passe si présent
        Admin admin = mapper.toEntity(dto);
        System.out.println("Mot de passe reçu : " + dto.getPassword());
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        admin.setPassword(encodedPassword);
        admin.setCreatedAt(Instant.now());
        admin.setLastLogin(Instant.now());
        System.out.println("Mot de passe encodé : " + encodedPassword);
        return mapper.toDto(repository.save(admin));
    }
    public AdminDTO getAdminById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admin not found")));
    }

    @Override
    public List<AdminDTO> getAllAdmins() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdminDTO updateAdmin(Long id, AdminDTO dto) {
        Admin admin = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admin not found"));

        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setRole(dto.getRole());

        return mapper.toDto(repository.save(admin));
    }

    @Override
    public void deleteAdmin(Long id) {
        repository.deleteById(id);
    }
}
