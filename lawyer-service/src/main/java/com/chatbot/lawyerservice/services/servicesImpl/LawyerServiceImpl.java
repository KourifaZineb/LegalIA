package com.ged.lawyerservice.services.servicesImpl;

import com.ged.lawyerservice.dtos.LawyerDTO;
import com.ged.lawyerservice.entities.Lawyer;
import com.ged.lawyerservice.entities.enums.Language;
import com.ged.lawyerservice.entities.enums.LawyerStatus;
import com.ged.lawyerservice.entities.enums.Speciality;
import com.ged.lawyerservice.mappers.LawyerMapper;
import com.ged.lawyerservice.repository.LawyerRepository;
import com.ged.lawyerservice.services.LawyerService;
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
            lawyer.setStatus(LawyerStatus.DISPONIBLE);
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
    public List<LawyerDTO> getLawyersByStatus(LawyerStatus status) {
        return  lawyerRepository.findByStatus(status).stream()
                .map(lawyerMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LawyerDTO> getLawyersByMinimumRating(Float minRating) {
        List<Lawyer> lawyers = lawyerRepository.findByRatingGreaterThanEqual(minRating);
        return lawyers.stream()
                .map(lawyerMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LawyerDTO updateLawyer(Long id, LawyerDTO lawyerDTO) {
        Lawyer lawyer = lawyerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lawyer not found"));

        // Tu peux aussi valider si lawyerDTO.getLawyerId() est cohérent avec l’id
        lawyer.setName(lawyerDTO.getName());
        lawyer.setEmail(lawyerDTO.getEmail());
        lawyer.setPhoneNumber(lawyerDTO.getPhoneNumber());
        lawyer.setLanguages(lawyerDTO.getLanguages());
        lawyer.setStatus(lawyerDTO.getStatus());
        lawyer.setSpecialization(lawyerDTO.getSpecialization());
        lawyer.setHourlyRate(lawyerDTO.getHourlyRate());
        lawyer.setRating(lawyerDTO.getRating());

        Lawyer updated = lawyerRepository.save(lawyer);
        return lawyerMapper.convertToDTO(updated);
    }


    @Override
    public void deleteLawyer(Long id) {
        if (!lawyerRepository.existsById(id)) {
            throw new RuntimeException("Lawyer not found");
        }
        lawyerRepository.deleteById(id);
    }

    @Override
    public boolean authenticateLawyer(String email, String password) {
        Optional<Lawyer> lawyerOpt = lawyerRepository.findByEmail(email);
        return lawyerOpt.map(lawyer -> lawyer.getPassword().equals(password)).orElse(false);
    }

    @Override
    public void updateLawyerAvailability(Long lawyerId, String status) {
        Lawyer lawyer = lawyerRepository.findById(lawyerId)
                .orElseThrow(() -> new RuntimeException("Lawyer not found"));

        try {
            lawyer.setStatus(LawyerStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }

        lawyerRepository.save(lawyer);
    }
}
