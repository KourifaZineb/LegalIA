package com.chatbot.userservice.servicesImpl;

import com.chatbot.userservice.dtos.LawyerDTO;
import com.chatbot.userservice.dtos.UserDTO;
import com.chatbot.userservice.entities.Lawyer;
import com.chatbot.userservice.enums.Language;
import com.chatbot.userservice.enums.Speciality;
import com.chatbot.userservice.enums.lawyerStatus;
import com.chatbot.userservice.enums.userStatus;
import com.chatbot.userservice.mappers.LawyerMapper;
import com.chatbot.userservice.repository.LawyerRepository;
import com.chatbot.userservice.services.LawyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LawyerServiceImpl implements LawyerService {

    @Autowired
    private LawyerRepository lawyerRepository;

    @Autowired
    private LawyerMapper lawyerMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LawyerDTO createLawyer(LawyerDTO lawyerDTO) {
        Lawyer lawyer = lawyerMapper.convertToEntity(lawyerDTO);

        lawyer.setPassword(passwordEncoder.encode(lawyer.getPassword()));
        lawyer.setCreatedAt(LocalDateTime.now());
        lawyer.setLastLogin(LocalDateTime.now());
        if (lawyer.getStatus() == null) {
            lawyer.setStatus(lawyerStatus.DISPONIBLE);
        }
        if (lawyer.getLanguages() == null) {
            lawyer.setLanguages(Language.FRANÇAIS);
        }

        Lawyer savedLawyer = lawyerRepository.save(lawyer);

        return lawyerMapper.convertToDTO(savedLawyer);
    }

    @Override
    public Optional<LawyerDTO> getLawyerById(Long id) {
        return lawyerRepository.findById(id)
                .map(lawyerMapper::convertToDTO);
    }

    @Override
    public Optional<LawyerDTO> getLawyerByEmail(String email) {
        return lawyerRepository.findByEmail(email)
                .map(lawyerMapper::convertToDTO);
    }

    @Override
    public List<LawyerDTO> getAllLawyers() {
        return lawyerRepository.findAll().stream()
                .map(lawyerMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LawyerDTO> getLawyersBySpecialization(Speciality specialization) {
        return lawyerRepository.findBySpecialization(specialization).stream()
                .map(lawyerMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LawyerDTO> getLawyersByStatus(lawyerStatus status) {
        return  lawyerRepository.findByStatus(status).stream()
                .map(lawyerMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LawyerDTO> getLawyersByMinimumRating(Float minRating) {
        return null;
    }

}
