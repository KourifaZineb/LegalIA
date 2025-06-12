package com.ged.lawyerservice.services;

import com.ged.lawyerservice.dtos.LawyerDTO;
import com.ged.lawyerservice.entities.enums.LawyerStatus;
import com.ged.lawyerservice.entities.enums.Speciality;

import java.util.List;
import java.util.Optional;

public interface LawyerService {
    LawyerDTO createLawyer(LawyerDTO lawyerDTO);
    Optional<LawyerDTO> getLawyerById(Long id);
    Optional<LawyerDTO> getLawyerByEmail(String email);
    List<LawyerDTO> getAllLawyers();
    List<LawyerDTO> getLawyersBySpecialization(Speciality specialization);
    List<LawyerDTO> getLawyersByStatus(LawyerStatus status);
    List<LawyerDTO> getLawyersByMinimumRating(Float minRating);
    LawyerDTO updateLawyer(Long id, LawyerDTO lawyerDTO);
    void deleteLawyer(Long id);
    boolean authenticateLawyer(String email, String password);
    void updateLawyerAvailability(Long lawyerId, String status);
}
