package com.chatbot.lawyerservice.services.servicesImpl;

import com.chatbot.commonlibrary.dtos.LawyerDTO;
import com.chatbot.commonlibrary.enums.LawyerStatus;
import com.chatbot.commonlibrary.exception.NotFoundException;
import com.chatbot.lawyerservice.mapper.LawyerMapper;
import com.chatbot.lawyerservice.repository.LawyerRepository;
import com.chatbot.lawyerservice.services.LawyerService;
import com.chatbot.lawyerservice.model.Lawyer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LawyerServiceImpl implements LawyerService {
    private final LawyerRepository repository;
    private final LawyerMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LawyerDTO createLawyer(LawyerDTO dto) {
        Lawyer lawyer = mapper.toEntity(dto);
        System.out.println("Mot de passe reçu : " + dto.getPassword());
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        lawyer.setPassword(encodedPassword);
        System.out.println("Mot de passe encodé : " + encodedPassword);
        lawyer.setCreatedAt(Instant.now());
        lawyer.setLastLogin(Instant.now());
        return mapper.toDto(repository.save(lawyer));
    }

    @Override
    public LawyerDTO getLawyerById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NotFoundException("Lawyer not found"));
    }

    @Override
    public List<LawyerDTO> getAllLawyers() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public List<LawyerDTO> getLawyersByStatus(LawyerStatus status) {
        return repository.findByStatus(status).stream().map(mapper::toDto).toList();
    }

    @Override
    public LawyerDTO updateLawyer(Long id, LawyerDTO dto) {
        Lawyer existing = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lawyer not found"));

        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        if (dto.getSolde() != null) existing.setSolde(dto.getSolde());
        if (dto.getPhoneNumber() != null) existing.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getAdresse() != null) existing.setAdresse(dto.getAdresse());
        if (dto.getStatus() != null) existing.setStatus(dto.getStatus());
        if (dto.getSpecialization() != null) existing.setSpecialization(dto.getSpecialization());
        if (dto.getLanguages() != null) existing.setLanguages(dto.getLanguages());
        if (dto.getHourlyRate() != null) existing.setHourlyRate(dto.getHourlyRate());

        return mapper.toDto(repository.save(existing));
    }

    @Override
    public void deleteLawyer(Long id) {
        repository.deleteById(id);
    }
}